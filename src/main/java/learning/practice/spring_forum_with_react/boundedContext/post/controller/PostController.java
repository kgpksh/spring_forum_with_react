package learning.practice.spring_forum_with_react.boundedContext.post.controller;

import jakarta.validation.Valid;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.*;
import learning.practice.spring_forum_with_react.boundedContext.post.service.CommentService;
import learning.practice.spring_forum_with_react.boundedContext.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/list")
    List<PostList> showPostPage(@RequestParam(required = false) String category, @RequestParam Long oldestId) {
        if (category== null) {
            return postService.readPostList(oldestId);
        }
        return postService.readPostList(category, oldestId);
    }

    @GetMapping("/view")
    PostView showPost(@RequestParam Long postId) {
        return postService.getPostView(postId);
    }

    @PostMapping("/comment")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<List<CommentView>> createComment(@RequestBody @Valid CommentCreateForm commentCreateForm) {
        return ResponseEntity.ok(commentService.createComment(commentCreateForm));
    }

    @PutMapping("/comment")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<List<CommentView>> modifyComment(@RequestBody @Valid CommentUpdateForm commentUpdateForm) {
        return ResponseEntity.ok(commentService.updateComment(commentUpdateForm));
    }

    @PostMapping("/post")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity post(@RequestBody @Valid PostSaveForm postSaveForm) {
        postService.savePost(postSaveForm);
        return ResponseEntity.ok("success");
    }
}
