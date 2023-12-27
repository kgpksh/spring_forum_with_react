package learning.practice.spring_forum_with_react.boundedContext.post.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class CommentViewImpl implements CommentView {
    private long id;
    private String comment;
    private Date lastModifiedDate;
    private String author;
}
