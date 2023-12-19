package learning.practice.spring_forum_with_react.boundedContext.post.service;

import learning.practice.spring_forum_with_react.boundedContext.post.dto.PostReadingList;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.SinglePostInList;
import learning.practice.spring_forum_with_react.boundedContext.post.entity.Post;
import learning.practice.spring_forum_with_react.boundedContext.post.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final CategoryService categoryService;
    private final PostRepository postRepository;
    private final int SELECT_RANGE = 500;

    public PostReadingList readPostList(String category, long oldestId) {
        long categoryId = categoryService.selectCategoryId(category);
        Optional<List<Post>> selectResult = postRepository.findPagesBySubject(categoryId, oldestId, SELECT_RANGE);

        if (selectResult.isEmpty()) {
            PostReadingList result = new PostReadingList();
            result.setPosts(new ArrayList<>());
            result.setOldestId(oldestId);
            return result;
        }

        List<Post> selectedPostsFromDb = selectResult.get();
        List<SinglePostInList> posts = new ArrayList<>();
        for (Post post : selectedPostsFromDb){
            SinglePostInList singlePost = new SinglePostInList();
            singlePost.setLastModifiedDate(post.getLastModifiedDate());
            singlePost.setCategory(categoryService.convertIdToCategory((long)post.getCategory()));
            singlePost.setAuthor(post.getAuthor());
            singlePost.setTitle(post.getTitle());
            singlePost.setView(post.getView());

            posts.add(singlePost);
        }

        PostReadingList result = new PostReadingList();
        result.setPosts(posts);
        result.setOldestId(selectedPostsFromDb.get(selectedPostsFromDb.size() - 1).getId());
        return result;
    }
}