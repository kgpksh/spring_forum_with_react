package learning.practice.spring_forum_with_react.boundedContext.post.entity;

import jakarta.persistence.Entity;
import learning.practice.spring_forum_with_react.base.baseEntity.BaseEntity;
import lombok.Data;

@Data
@Entity
public class Post extends BaseEntity {
    private String author;
    private int subject;
    private String title;
    private String content;
    private long view;
}
