package learning.practice.spring_forum_with_react.boundedContext.post.controller;

import learning.practice.spring_forum_with_react.boundedContext.post.dto.PostList;
import learning.practice.spring_forum_with_react.boundedContext.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/list")
    List<PostList> showPostPage(@RequestParam(required = false) String category, @RequestParam Long oldestId) {
        if (category== null) {
            return postService.readPostList(oldestId);
        }
        return postService.readPostList(category, oldestId);
    }
}
