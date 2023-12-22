package learning.practice.spring_forum_with_react.boundedContext.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import learning.practice.spring_forum_with_react.boundedContext.post.validation.CategoryRequest;
import lombok.Getter;

@Getter
public class PostSaveForm {
    @NotBlank
    private String author;
    @CategoryRequest
    private int category;
    @NotBlank
    private String title;
    @NotNull
    private String content;
}
