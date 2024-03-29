package learning.practice.spring_forum_with_react.boundedContext.post.service;

import learning.practice.spring_forum_with_react.TestUtils;
import learning.practice.spring_forum_with_react.base.rq.Rq;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.CommentCreateForm;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.CommentUpdateForm;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.CommentView;
import learning.practice.spring_forum_with_react.boundedContext.post.repository.CommentRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class CommentServiceTest {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    @MockBean
    private Rq rq;

    @Test
    @Order(1)
    void createCommentTest() throws IllegalAccessException {
        CommentCreateForm commentCreateForm1 = new CommentCreateForm();
        CommentCreateForm commentCreateForm2 = new CommentCreateForm();
        TestUtils.setFieldValue(commentCreateForm1, 1L, "테스트 댓글1", "user1");
        TestUtils.setFieldValue(commentCreateForm2, 1L, "테스트 댓글2", "user2");

        commentService.createComment(commentCreateForm1);
        commentService.createComment(commentCreateForm2);

        assertThat(commentRepository.findById(1L).get().getComment()).isEqualTo("테스트 댓글1");
    }

    @Test
    @Order(2)
    void readCommentTest() {
        List<CommentView> commentList = commentService.getCommentInPost(1L);

        assertThat(commentList.size()).isEqualTo(2);
        assertThat(commentList.get(1).getComment()).isEqualTo("테스트 댓글2");
    }

    @Test
    @Order(3)
    void updateCommentTest() throws Exception {
        when(rq.isOwner(anyString())).thenReturn(true);
        CommentUpdateForm updateForm = new CommentUpdateForm();
        TestUtils.setFieldValue(updateForm, 1L, 1L, "바뀐 댓글1", "user1");

        commentService.updateComment(updateForm);

        assertThat(commentRepository.findById(1L).get().getComment()).isEqualTo( "바뀐 댓글1");
    }

    @Test
    @Order(4)
    void deleteCommentTest() throws Exception {
        when(rq.isOwner(anyString())).thenReturn(true);
        commentService.deleteComment(List.of(1L));

        List<CommentView> comments = commentService.getCommentInPost(1L);

        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getComment()).isEqualTo("테스트 댓글2");
    }

    @Test
    @Order(5)
    void deleteAllByPost() throws IllegalAccessException {
        CommentCreateForm commentCreateForm3 = new CommentCreateForm();
        CommentCreateForm commentCreateForm4 = new CommentCreateForm();
        TestUtils.setFieldValue(commentCreateForm3, 1L, "테스트 댓글3", "user1");
        TestUtils.setFieldValue(commentCreateForm4, 1L, "테스트 댓글4", "user2");

        commentService.deleteWithPost(1L);

        assertThat(commentRepository.findAll().size()).isEqualTo(0);
    }
}