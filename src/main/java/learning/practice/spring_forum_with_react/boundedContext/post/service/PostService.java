package learning.practice.spring_forum_with_react.boundedContext.post.service;

import learning.practice.spring_forum_with_react.boundedContext.post.dto.PostContent;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.PostList;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.PostSaveForm;
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

    public String getPostContent(long postId) {
        Optional<PostContent> content = postRepository.findContentById(postId);
        if (content.isEmpty()) {
            throw new IllegalArgumentException("존재 하지 않는 게시글입니다");
        }

        return content.get().getContent();
    }

    public Post savePost(PostSaveForm postSaveForm) {
        Post post = new Post();
        post.setCategory(postSaveForm.getCategory());
        post.setAuthor(postSaveForm.getAuthor());
        post.setTitle(postSaveForm.getTitle());
        post.setContent(postSaveForm.getContent());

        return postRepository.save(post);
    }

    public void deletePost(long postId) {
        postRepository.deleteById(postId);
        commentService.deleteWithPost(postId);
    }
}