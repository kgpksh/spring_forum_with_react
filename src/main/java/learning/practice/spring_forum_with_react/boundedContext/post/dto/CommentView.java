package learning.practice.spring_forum_with_react.boundedContext.post.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

@JsonDeserialize(as = CommentViewImpl.class)
public interface CommentView {
    long getId();
    String getComment();
    Date getLastModifiedDate();
    String getAuthor();
}
