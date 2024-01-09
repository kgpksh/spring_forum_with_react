package learning.practice.spring_forum_with_react.boundedContext.post.service;

import learning.practice.spring_forum_with_react.TestUtils;
import learning.practice.spring_forum_with_react.base.initData.TestData;
import learning.practice.spring_forum_with_react.base.rq.Rq;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.*;
import learning.practice.spring_forum_with_react.boundedContext.post.entity.Post;
import org.hibernate.QueryParameterException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

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
    @Autowired
    private CommentService commentService;
    @MockBean
    private Rq rq;

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

    @Test
    void getPostContentSuccessTest() throws IllegalAccessException {
        for(int i = 0; i < 10; i++) {
            CommentCreateForm commentCreateForm = new CommentCreateForm();
            TestUtils.setFieldValue(commentCreateForm, 1L, "테스트 댓글", "user1");

            commentService.createComment(commentCreateForm);
        }

        PostView postContent = postService.getPostView(1L);
        assertThat(postContent.getContent()).isEqualTo("content1");
        assertThat(postContent.getComments().size()).isEqualTo(10);
    }

    @Test
    void getPostContentFailTest() {
        assertThatThrownBy(() -> postService.getPostView(0L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void updatePostSuccessTest() throws Exception {
        when(rq.isOwner(anyString())).thenReturn(true);
        PostUpdateForm updateForm = new PostUpdateForm();
        TestUtils.setFieldValue(updateForm, 1L, "수정된 제목", "수정된 본문");

        postService.modifyPost(updateForm);
        assertThat(postService.getPostView(1L).getContent()).isEqualTo("수정된 본문");
    }

    @Test
    void updatePostFailByNonExistsTest() throws Exception {
        PostUpdateForm updateForm = new PostUpdateForm();
        TestUtils.setFieldValue(updateForm, 0L, "수정된 제목", "수정된 본문");

        assertThatThrownBy(() -> postService.modifyPost(updateForm))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deletePost() throws Exception {
        when(rq.isOwner(anyString())).thenReturn(true);
        for(int i = 0; i < 10; i++) {
            CommentCreateForm commentCreateForm = new CommentCreateForm();
            TestUtils.setFieldValue(commentCreateForm, 1L, "테스트 댓글", "user1");

            commentService.createComment(commentCreateForm);
        }

        postService.deletePost(1L);

        assertThat(postService.readPostList(2L).size()).isEqualTo(0);
        assertThat(commentService.getCommentInPost(1L).size()).isEqualTo(0);
    }
}