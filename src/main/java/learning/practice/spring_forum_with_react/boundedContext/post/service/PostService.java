package learning.practice.spring_forum_with_react.boundedContext.post.service;

import learning.practice.spring_forum_with_react.boundedContext.post.dto.PostList;
import learning.practice.spring_forum_with_react.boundedContext.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
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

    public List<PostList> readPostListByCategory(String category, long oldestId) {
        long categoryId = categoryService.selectCategoryId(category);
        Optional<List<PostList>> selectResult = postRepository.findPagesBySubject(categoryId, oldestId, SELECT_RANGE);

        if (selectResult.isEmpty()) {
            return new ArrayList<>();
        }

        List<PostList> result = selectResult.get();
        return result;
    }

    public List<PostList> readPostList(long oldestId) {
        Optional<List<PostList>> selectResult = postRepository.findPages(oldestId, SELECT_RANGE);

        if (selectResult.isEmpty()) {
            return new ArrayList<>();
        }

        return selectResult.get();
    }
}