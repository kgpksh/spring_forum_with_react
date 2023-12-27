package learning.practice.spring_forum_with_react.boundedContext.post.repository;

import learning.practice.spring_forum_with_react.boundedContext.post.dto.CommentView;
import learning.practice.spring_forum_with_react.boundedContext.post.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<CommentView> findAllByPostId(long postId);
    Optional<Comment> findById(long id);

    void deleteAllByPostId(long id);
}
