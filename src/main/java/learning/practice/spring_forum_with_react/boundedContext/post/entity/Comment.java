package learning.practice.spring_forum_with_react.boundedContext.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import learning.practice.spring_forum_with_react.base.baseEntity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(indexes = @Index(name = "idx_postId", columnList = "postId"))
@Where(clause = "deleted_date is null")
@SQLDelete(sql = "UPDATE comment set deleted_date = NOW() where id = ?")
public class Comment extends BaseEntity {
    @NotNull
    private String comment;
    @NotNull
    private long postId;
    @NotNull
    private String author;
}
