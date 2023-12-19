package learning.practice.spring_forum_with_react.boundedContext.post.service;

import learning.practice.spring_forum_with_react.boundedContext.post.dto.PostReadingList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class PostServiceTest {
    @Autowired
    private PostService postService;

    @Test
    void readPostListTest() {
        PostReadingList postReadingList = postService.readPostList("Book", 2401L);
        assertThat(postReadingList.getPosts().get(0).getTitle()).isEqualTo("title2397");
        assertThat(postReadingList.getOldestId()).isEqualTo(401L);
    }
}