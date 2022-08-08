package com.sparta.memoproject.controller;

import com.sparta.memoproject.dto.CommentDto;
import com.sparta.memoproject.dto.CommentRequestDto;
import com.sparta.memoproject.model.Comment;
import com.sparta.memoproject.repository.CommentRepository;
import com.sparta.memoproject.service.CommentService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;  // 필수적인 요소이기 때문에 final 선언


    @Secured("ROLE_USER")
    @PostMapping("/auth/comment/{id}")
    public Comment addComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.addComment(id, commentRequestDto);
    }

    @Secured("ROLE_USER")
    @PutMapping("/auth/comment/{id}/{commentId}")
    public Comment updateComment(@PathVariable Long id, @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.updateComment(id, commentId, commentRequestDto);
    }
    @Secured("ROLE_USER")
    @DeleteMapping("/auth/comment/{id}/{commentId}")
    public Boolean deleteComment(@PathVariable Long id, @PathVariable Long commentId) {
        return commentService.deleteComment(id, commentId);
    }

    @Secured("ROLE_USER")
    @PostMapping("/auth/comment/child/{commentId}")
    public Comment addChildComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.addChildComment(commentId, commentRequestDto);
    }

    @Secured("ROLE_USER")
    @PatchMapping("/auth/comment/child/{commentId}")
    public Comment updateChildComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.updateChildComment(commentId, commentRequestDto);
    }
    @Secured("ROLE_USER")
    @DeleteMapping("/auth/comment/child/{commentId}")
    public Boolean deleteComment(@PathVariable Long commentId) {
        return commentService.deleteChildComment(commentId);
    }
    @GetMapping("/comment")
    public List<Comment> readComment() {
        return commentRepository.findAllByParent_IdIsNullOrderByModifiedAtDesc();
    }//parent_id

    @GetMapping("/comment/{Id}")
    public CommentDto readCommentHeart(@PathVariable Long Id) {
        return commentService.readCommentHeart(Id);
    }

    @GetMapping("/comment/child/{Id}")
    public CommentDto readChildCommentHeart(@PathVariable Long Id){
        return commentService.readChildCommentHeart(Id);
    }


}

//    @GetMapping("/api/memos")
//    public List<Memo> readMemo(){
//        return memoRepository.findAllByOrderByModifiedAtDesc();
//    }
//}
