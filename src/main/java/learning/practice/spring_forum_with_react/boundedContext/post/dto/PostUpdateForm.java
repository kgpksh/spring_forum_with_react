package learning.practice.spring_forum_with_react.boundedContext.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostUpdateForm {
    @NotNull
    private long id;
    @NotBlank
    private String title;
    @NotNull
    private String content;
}
