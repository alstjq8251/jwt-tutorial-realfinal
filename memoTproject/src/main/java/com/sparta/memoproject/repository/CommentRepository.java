package com.sparta.memoproject.repository;

import com.sparta.memoproject.model.Comment;
import com.sparta.memoproject.model.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findAllByParent_IdIsNullOrderByModifiedAtDesc();


    Optional<Comment> findByIdAndParent_IdIsNotNull(Long childcommentId);


    List<Comment> findAllByMemberName(String nickname);

    List<Comment> findAllByMemberNameAndParent_IdIsNotNull(String nickname);

    Optional<Comment> findByIdAndParent_IdIsNull(Long commentId);

    List<Comment> findAllByMemberNameAndParent_IdIsNull(String nickname);
}