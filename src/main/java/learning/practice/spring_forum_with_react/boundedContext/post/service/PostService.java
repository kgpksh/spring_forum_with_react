package learning.practice.spring_forum_with_react.boundedContext.post.service;

import learning.practice.spring_forum_with_react.base.rq.Rq;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.*;
import learning.practice.spring_forum_with_react.boundedContext.post.entity.Post;
import learning.practice.spring_forum_with_react.boundedContext.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.QueryParameterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final CategoryService categoryService;
    private final PostRepository postRepository;
    private final int SELECT_RANGE = 500;

    private final CommentService commentService;
    private final Rq rq;

    public List<PostList> readPostList(String category, long oldestId) throws QueryParameterException {
        try {
            long categoryId = categoryService.selectCategoryId(category);
            Optional<List<PostList>> selectResult = postRepository.findPagesBySubject(categoryId, getOldestId(oldestId), SELECT_RANGE);

            if (selectResult.isEmpty()) {
                return new ArrayList<>();
            }

            List<PostList> result = selectResult.get();
            return result;
        } catch (QueryParameterException queryParameterException) {
            throw queryParameterException;
        }
    }

    public List<PostList> readPostList(long oldestId) {
        Optional<List<PostList>> selectResult = postRepository.findPages(getOldestId(oldestId), SELECT_RANGE);

        if (selectResult.isEmpty()) {
            return new ArrayList<>();
        }

        return selectResult.get();
    }

    private long getOldestId(long oldestId) {
        if (oldestId == -1L) {
            return Long.MAX_VALUE;
        }

        return oldestId;
    }

    public PostView getPostView(long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new IllegalArgumentException("존재 하지 않는 게시글입니다");
        }

        List<CommentView> comments = commentService.getCommentInPost(postId);
        Post unpackedPost = post.get();

        long nextView = unpackedPost.getView();
        unpackedPost.setView(++nextView);

        PostView postView = new PostView();
        postView.setContent(unpackedPost.getContent());
        postView.setComments(comments);
        postView.setView(nextView);

        return postView;
    }

    public Post savePost(PostSaveForm postSaveForm) {
        Post post = new Post();
        post.setCategory(postSaveForm.getCategory());
        post.setAuthor(postSaveForm.getAuthor());
        post.setTitle(postSaveForm.getTitle());
        post.setContent(postSaveForm.getContent());

        return postRepository.save(post);
    }

    public Post modifyPost(PostUpdateForm updateForm) throws Exception {
        Post post = validate(updateForm.getId());

        post.setTitle(updateForm.getTitle());
        post.setContent(updateForm.getContent());

        return postRepository.save(post);
    }

    private Post validate(long postId) throws Exception {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 게시글 입니다");
        }

        Post unpackedPost = post.get();

        if (!rq.isOwner(unpackedPost.getAuthor())) {
            throw new IllegalAccessException("이 게시글의 작성자가 아닙니다.");
        }

        return unpackedPost;
    }

    public void deletePost(long postId) throws Exception {
        Post post = validate(postId);
        postRepository.deleteById(postId);
        commentService.deleteWithPost(postId);
    }
}