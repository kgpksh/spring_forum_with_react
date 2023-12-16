package learning.practice.spring_forum_with_react.boundedContext.post.repository;

import learning.practice.spring_forum_with_react.boundedContext.post.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
}
