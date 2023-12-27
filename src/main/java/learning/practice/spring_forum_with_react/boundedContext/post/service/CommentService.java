package learning.practice.spring_forum_with_react.boundedContext.post.service;

import learning.practice.spring_forum_with_react.boundedContext.post.dto.CommentCreateForm;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.CommentUpdateForm;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.CommentView;
import learning.practice.spring_forum_with_react.boundedContext.post.entity.Comment;
import learning.practice.spring_forum_with_react.boundedContext.post.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;

    public List<CommentView> getCommentInPost(long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    public Comment createComment(CommentCreateForm commentInput) {
        Comment comment = new Comment();
        comment.setComment(commentInput.getComment());
        comment.setAuthor(commentInput.getAuthor());
        comment.setPostId(commentInput.getPostId());

        return commentRepository.save(comment);
    }

    public Comment updateComment(CommentUpdateForm commentUpdate) {
        Optional<Comment> comment = commentRepository.findById(commentUpdate.getId());

        if (comment.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 게시글 입니다");
        }

        Comment unpackedComment = comment.get();
        unpackedComment.setComment(commentUpdate.getComment());
        return commentRepository.save(unpackedComment);
    }

    public void deleteComment(List<Long> ids) {
        commentRepository.deleteAllById(ids);
    }
}
