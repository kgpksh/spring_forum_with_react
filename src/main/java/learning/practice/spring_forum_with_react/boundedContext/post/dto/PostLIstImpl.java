package learning.practice.spring_forum_with_react.boundedContext.post.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class PostLIstImpl implements PostList {
    private long id;
    private Date last_modified_date;
    private String author;
    private int category;
    private String title;
    private long view;
}
