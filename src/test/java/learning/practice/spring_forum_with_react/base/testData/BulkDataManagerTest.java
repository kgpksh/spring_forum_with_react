package learning.practice.spring_forum_with_react.base.testData;

import learning.practice.spring_forum_with_react.boundedContext.post.entity.Post;
import learning.practice.spring_forum_with_react.boundedContext.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Tag("bulkDbTest")
@ActiveProfiles("highLoad")
@SpringBootTest
class BulkDataManagerTest {
    @Autowired
    private BulkDataManager bulkDataManager;
    @Autowired
    private PostRepository postRepository;

    private List<Post> createData(int size) {
        String[] subjects = {"Cook", "Book", "Coding", "Humor"};
        String author = "author";
        String title = "title";
        String content = "content";

        List<Post> sampleData = new ArrayList<>(size);
        for (int i = 1; i < size + 1; i++) {
            Post post = new Post();
            post.setAuthor(author + i % 50);
            post.setCategory(i % 4);
            post.setTitle(title + i);
            post.setContent(content + i);
            sampleData.add(post);
        }

        return sampleData;
    }
    @Test
    @DisplayName("jdbc 배치 단건 입력")
    void insertSingle() {
        int size = 1;
        List<Post> sampleData = createData(size);
        bulkDataManager.saveBulk(sampleData, size);

        Optional<Post> selected = postRepository.findById(1L);
        assertThat(selected.isPresent()).isTrue();
        assertThat(selected.get().getAuthor()).isEqualTo("author1");
    }

    @Test
    @DisplayName("jdbc 대량 입력")
    void insertBulk() {
        long start = System.currentTimeMillis();
        int size = 1000000;
        List<Post> sampleData = createData(size);
        bulkDataManager.saveBulk(sampleData, size);

        System.out.println("대량 입력시간 : " + (System.currentTimeMillis() - start));
    }
}