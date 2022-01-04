package com.example.facebookspringemeka.Repository;

import com.example.facebookspringemeka.Models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostPostId(Long postId);
    Comment findCommentById(Long commentId);
    @Transactional
    void deleteCommentById(Long commentId);
}
