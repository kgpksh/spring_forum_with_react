package learning.practice.spring_forum_with_react.boundedContext.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PostView {
    private String content;
    private List<CommentView> comments;
    private long view;
}
