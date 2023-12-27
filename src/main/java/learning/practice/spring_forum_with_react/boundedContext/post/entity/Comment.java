package learning.practice.spring_forum_with_react.boundedContext.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import learning.practice.spring_forum_with_react.base.baseEntity.BaseEntity;
import lombok.Data;

@Data
@Entity
@Table(indexes = @Index(name = "idx_postId", columnList = "postId"))
public class Comment extends BaseEntity {
    @NotNull
    private String comment;
    @NotNull
    private long postId;
    @NotNull
    private String author;
}
