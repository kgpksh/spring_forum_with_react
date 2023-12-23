package learning.practice.spring_forum_with_react.boundedContext.post.service;

import learning.practice.spring_forum_with_react.TestUtils;
import learning.practice.spring_forum_with_react.base.initData.TestData;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.PostList;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.PostSaveForm;
import learning.practice.spring_forum_with_react.boundedContext.post.entity.Post;
import org.hibernate.QueryParameterException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class PostServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private TestData testData;

    @Test
    void readPostListByCategoryTest() {
        List<PostList> postReadingList = postService.readPostList("Book", 2401L);
        assertThat(postReadingList.get(0).getTitle()).isEqualTo("title2397");
        assertThat(postReadingList.get(postReadingList.size() - 1).getId()).isEqualTo(401L);
    }

    @Test
    void readPostListByCategoryFailTest() {
        assertThatThrownBy(() -> postService.readPostList("aba", 2401L))
                .isInstanceOf(QueryParameterException.class);
    }

    @Test
    void readPostListWithoutCategory() {
        List<PostList> postList = postService.readPostList(2000L);
        assertThat(postList.get(0).getTitle()).isEqualTo("title1999");
        assertThat(postList.get(postList.size() - 1).getId()).isEqualTo(1500L);
    }

    @Test
    void readPostListUnderPageUnitLength() {
        List<PostList> postList = postService.readPostList(2);
        assertThat(postList.get(0).getTitle()).isEqualTo("title1");
        assertThat(postList.size()).isEqualTo(1);
    }

    @Test
    void savePostTestSuccess() throws Exception{
        PostSaveForm postSaveForm = new PostSaveForm();
        TestUtils.setFieldValue(postSaveForm, "newAuthor", 1, "newTitle", "newContent");
       Post post = postService.savePost(postSaveForm);

       assertThat(post.getAuthor()).isEqualTo("newAuthor");
    }
}