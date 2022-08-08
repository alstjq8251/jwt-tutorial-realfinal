package com.sparta.memoproject.service;

import com.sparta.memoproject.dto.CommentDto;
import com.sparta.memoproject.dto.CommentRequestDto;
import com.sparta.memoproject.model.Comment;
import com.sparta.memoproject.model.Memo;
import com.sparta.memoproject.repository.CommentRepository;
import com.sparta.memoproject.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor //final로 선언한 변수가 있으면 꼭 생성해달라는 것
@Service
public class CommentService {


    private final CommentRepository commentRepository; // [2번]update메소드 작성 전에 id에 맞는 값을 찾으려면 find를 써야하는데 find를 쓰기위해서는 Repository가 있어야한다.
    private final MemoRepository memoRepository;
    private final MemoService memoService;


    @Transactional
    public Comment addComment(Long id, CommentRequestDto commentRequestDto) {
        Memo memo = memoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        String memberName = memoService.getNickname();
        Comment comment = new Comment(memo, memberName, commentRequestDto);

        memo.addComment(commentRepository.save(comment));

        return comment;
    }

    @Transactional
    public Comment updateComment(Long id, Long commentId, CommentRequestDto commentRequestDto) {
        Memo memo = memoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if(!memoService.getNickname().equals(memo.getMemberName())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
        comment.setComment(commentRequestDto);
        return comment;
    }

    @Transactional
    public Boolean deleteComment(Long id, Long commentId) {
        Memo memo = memoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        if(!memoService.getNickname().equals(memo.getMemberName())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
        memo.deleteComment(comment);
        return true;

    }

    @Transactional
    public Comment addChildComment(Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        String memberName = memoService.getNickname();
        Comment childcomment = new Comment(comment, memberName, commentRequestDto);

        comment.addComment(commentRepository.save(childcomment));

        return childcomment;
    }

    public CommentDto readCommentHeart(Long Id) {
        Comment comment = commentRepository.findById(Id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        CommentDto commentDto = new CommentDto(comment);

        return commentDto;
    }

    public CommentDto readChildCommentHeart(Long Id) {
        Comment comment = commentRepository.findByIdAndParent_IdIsNotNull(Id)
                .orElseThrow(() -> new IllegalArgumentException("해당 대댓글이 존재하지 않습니다."));
        CommentDto commentDto = new CommentDto(comment);

        return commentDto;
    }

    @Transactional
    public Comment updateChildComment(
            Long commentId, CommentRequestDto commentRequestDto
    ) {
        Comment comment = commentRepository.findByIdAndParent_IdIsNull(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 대댓글이 존재하지 않습니다."));
        if(!memoService.getNickname().equals(comment.getMemberName()))
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        comment.setComment(commentRequestDto);
        return commentRepository.save(comment);
    }

    public Boolean deleteChildComment(Long commentId) {
        Comment comment = commentRepository.findByIdAndParent_IdIsNull(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 대댓글이 존재하지 않습니다."));
        if(!memoService.getNickname().equals(comment.getMemberName()))
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        commentRepository.delete(comment);
        return true;
    }
}

