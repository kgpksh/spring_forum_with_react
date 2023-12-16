package learning.practice.spring_forum_with_react.base.testData;

import learning.practice.spring_forum_with_react.boundedContext.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BulkDataManager {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveBulk(List<Post> postList, int size) {
        String sql = """
                INSERT INTO post (created_date, last_modified_date, subject, author, title, content) VALUES (NOW(), NOW(), ?, ?, ?, ?)
                """;

        int batchSize = 20000;
        for (int i = 0; i < size; i+= batchSize) {
            List<Post> batchList = postList.subList(i, Math.min(i + batchSize, size));

            jdbcTemplate.batchUpdate(sql,
                    batchList,
                    size,
                    (PreparedStatement ps, Post post) -> {
                        ps.setInt(1, post.getSubject());
                        ps.setString(2, post.getAuthor());
                        ps.setString(3, post.getTitle());
                        ps.setString(4, post.getContent());
                    });
        }
    }
}
