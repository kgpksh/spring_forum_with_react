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
    public void createCategoryTable() {
        String[] categories = {"Cook", "Book", "Coding", "Humor"};

        for (String category : categories) {
            String sql = "INSERT INTO category (category) VALUES (?);";
            jdbcTemplate.update(sql, category);
        }
    }

    @Transactional
    public void createPostTable(List<Post> postList, int size) {
        dropPostTableIfExists();
        String sql =
                "CREATE TABLE post (" +
                "id BIGINT NOT NULL AUTO_INCREMENT," +
                "created_date DATETIME," +
                "last_modified_date DATETIME," +
                "deleted_date DATETIME," +
                "title VARCHAR(255) NOT NULL," +
                "category INT NOT NULL," +
                "author VARCHAR(255) NOT NULL," +
                "content VARCHAR(255) NOT NULL," +
                "view BIGINT NOT NULL DEFAULT 0," +
                "PRIMARY KEY(id, category)" +
                ") PARTITION BY LIST (category) (" +
                "PARTITION Cook VALUES IN (1)," +
                "PARTITION Book VALUES IN (2)," +
                "PARTITION Coding VALUES IN (3)," +
                "PARTITION Humor VALUES IN (4));";

        jdbcTemplate.execute(sql);

        saveBulk(postList, size);
    }

    private void dropPostTableIfExists() {
        String sql = "DROP TABLE IF EXISTS post;";
        jdbcTemplate.execute(sql);
    }

    public void saveBulk(List<Post> postList, int size) {
        String sql = """
                INSERT INTO post (created_date, last_modified_date, category, author, title, content) VALUE (NOW(), NOW(), ?, ?, ?, ?)
                """;

        int batchSize = 20000;
        for (int i = 0; i < size; i+= batchSize) {
            List<Post> batchList = postList.subList(i, Math.min(i + batchSize, size));

            jdbcTemplate.batchUpdate(sql,
                    batchList,
                    size,
                    (PreparedStatement ps, Post post) -> {
                        ps.setInt(1, post.getCategory());
                        ps.setString(2, post.getAuthor());
                        ps.setString(3, post.getTitle());
                        ps.setString(4, post.getContent());
                    });
        }
    }
}
