package learning.practice.spring_forum_with_react.boundedContext.post.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

@JsonDeserialize(as = PostLIstImpl.class)
public interface PostList {
    long getId();
    Date getLast_modified_date();
    String getAuthor();
    int getCategory();
    String getTitle();
    long getView();
}
