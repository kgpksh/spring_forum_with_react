package learning.practice.spring_forum_with_react.boundedContext.post.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentUpdateForm {
    @NotNull
    private long id;
    @NotNull
    private String comment;
}
