package learning.practice.spring_forum_with_react.boundedContext.post.dto;

import java.util.Date;

public interface PostList {
    long getId();
    Date getLastModifiedDate();
    String getAuthor();
    int getCategory();
    String getTitle();
    long getView();
}
