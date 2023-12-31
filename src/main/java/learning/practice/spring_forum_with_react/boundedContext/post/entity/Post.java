package learning.practice.spring_forum_with_react.boundedContext.post.entity;

import jakarta.persistence.Entity;
import learning.practice.spring_forum_with_react.base.baseEntity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Where(clause = "deleted_date is null")
@SQLDelete(sql = "UPDATE post set deleted_date = NOW() where id = ?")
public class Post extends BaseEntity {
    private String author;
    private int category;
    private String title;
    private String content;
    private long view;
}
