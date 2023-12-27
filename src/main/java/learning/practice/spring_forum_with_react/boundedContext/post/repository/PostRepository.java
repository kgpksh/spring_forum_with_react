package learning.practice.spring_forum_with_react.boundedContext.post.repository;

import learning.practice.spring_forum_with_react.boundedContext.post.dto.PostContent;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.PostList;
import learning.practice.spring_forum_with_react.boundedContext.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = """
                SELECT id, last_modified_date, author, category, title, view
                FROM post WHERE category = :category and id < :oldestId ORDER BY id DESC LIMIT :selectRange
            """, nativeQuery = true)
    Optional<List<PostList>> findPagesBySubject(@Param("category") long category, @Param("oldestId") long oldestId, @Param("selectRange") int selectRange);

    @Query(value = """
                    SELECT id, last_modified_date, author, category, title, view
                    FROM post WHERE id < :oldestId ORDER BY id DESC LIMIT :selectRange
                """, nativeQuery = true)
    Optional<List<PostList>> findPages(@Param("oldestId") long oldestId, @Param("selectRange") int selectRange);

    Optional<PostContent> findContentById(long id);
}
