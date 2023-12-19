package learning.practice.spring_forum_with_react.boundedContext.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SinglePostInList {
    private Date lastModifiedDate;
    private String author;
    private String category;
    private String title;
    private long view;
}
