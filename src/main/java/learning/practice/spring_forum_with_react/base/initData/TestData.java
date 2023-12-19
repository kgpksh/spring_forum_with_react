package learning.practice.spring_forum_with_react.base.initData;

import learning.practice.spring_forum_with_react.base.testData.BulkDataManager;
import learning.practice.spring_forum_with_react.boundedContext.post.entity.Post;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile({"dev", "test"})
public class TestData {
    private List<Post> createData(int size) {
        String[] subjects = {"Cook", "Book", "Coding", "Humor"};
        String author = "author";
        String title = "title";
        String content = "content";

        List<Post> sampleData = new ArrayList<>(size);
        for (int i = 1; i < size + 1; i++) {
            Post post = new Post();
            post.setAuthor(author + (i % 50));
            post.setCategory((i % 4) + 1);
            post.setTitle(title + i);
            post.setContent(content + i);
            sampleData.add(post);
        }

        return sampleData;
    }

    @Bean
    @Transactional
    CommandLineRunner initData(BulkDataManager bulkDataManager) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                bulkDataManager.createCategoryTable();

                int size = 2420;
                List<Post> sampleData = createData(size);
                bulkDataManager.createPostTable(sampleData, size);
            }
        };
    }
}
