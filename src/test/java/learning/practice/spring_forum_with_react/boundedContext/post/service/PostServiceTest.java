package learning.practice.spring_forum_with_react.boundedContext.post.service;

import learning.practice.spring_forum_with_react.boundedContext.post.dto.PostList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class PostServiceTest {
    @Autowired
    private PostService postService;

    @Test
    void readPostListByCategoryTest() {
        List<PostList> postReadingList = postService.readPostListByCategory("Book", 2401L);
        assertThat(postReadingList.get(0).getTitle()).isEqualTo("title2397");
        assertThat(postReadingList.get(postReadingList.size() - 1).getId()).isEqualTo(401L);
    }

    @Test
    void readPostListWithoutCategory() {
        List<PostList> postList = postService.readPostList(2000L);
        assertThat(postList.get(0).getTitle()).isEqualTo("title1999");
        assertThat(postList.get(postList.size() - 1).getId()).isEqualTo(1500L);
    }
}