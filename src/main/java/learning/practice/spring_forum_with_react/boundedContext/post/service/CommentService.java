package learning.practice.spring_forum_with_react.boundedContext.post.service;

import learning.practice.spring_forum_with_react.base.rq.Rq;
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
    private final Rq rq;

    public List<CommentView> getCommentInPost(long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    public List<CommentView> createComment(CommentCreateForm commentInput) {
        Comment comment = new Comment();
        comment.setComment(commentInput.getComment());
        comment.setAuthor(commentInput.getAuthor());
        comment.setPostId(commentInput.getPostId());

        commentRepository.save(comment);
        return getCommentInPost(commentInput.getPostId());
    }

    public List<CommentView> updateComment(CommentUpdateForm commentUpdate) throws Exception {
        Comment comment = validate(commentUpdate.getId());
        comment.setComment(commentUpdate.getComment());
        commentRepository.save(comment);
        return getCommentInPost(commentUpdate.getPostId());
    }

    public void deleteComment(List<Long> ids) throws Exception{
        for (long id : ids) {
            validate(id);
        }
        commentRepository.deleteAllById(ids);
    }

    private Comment validate(long id) throws Exception {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 댓글 입니다");
        }

        Comment unpackedComment = comment.get();

        if (!rq.isOwner(unpackedComment.getAuthor())) {
            throw new IllegalAccessException("이 댓글의 작성자가 아닙니다.");
        }

        return unpackedComment;
    }

    public void deleteWithPost(long postId) {
        commentRepository.deleteAllByPostId(postId);
    }
}
