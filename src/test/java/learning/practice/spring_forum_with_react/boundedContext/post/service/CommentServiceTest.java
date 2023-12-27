package learning.practice.spring_forum_with_react.boundedContext.post.service;

import learning.practice.spring_forum_with_react.TestUtils;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.CommentCreateForm;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.CommentUpdateForm;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.CommentView;
import learning.practice.spring_forum_with_react.boundedContext.post.repository.CommentRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
    void updateCommentTest() throws IllegalAccessException {
        CommentUpdateForm updateForm = new CommentUpdateForm();
        TestUtils.setFieldValue(updateForm, 1L, "바뀐 댓글1");

        commentService.updateComment(updateForm);

        assertThat(commentRepository.findById(1L).get().getComment()).isEqualTo( "바뀐 댓글1");
    }

    @Test
    @Order(4)
    void deleteCommentTest() {
        commentService.deleteComment(1L);

        List<CommentView> comments = commentService.getCommentInPost(1L);

        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getComment()).isEqualTo("테스트 댓글2");
    }
}