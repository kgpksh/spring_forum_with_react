package learning.practice.spring_forum_with_react.boundedContext.post.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentCreateForm {
    @NotNull
    private long postId;
    @NotNull
    private String comment;
    @NotNull
    private String author;
}
