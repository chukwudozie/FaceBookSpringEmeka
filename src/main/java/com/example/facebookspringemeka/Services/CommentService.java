package com.example.facebookspringemeka.Services;

import com.example.facebookspringemeka.DTO.CommentDTO;
import com.example.facebookspringemeka.Models.Comment;
import com.example.facebookspringemeka.Models.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    boolean createNewComment(Long userId, Long postId, Comment comment);
    List<CommentDTO> getAllCommentsForPost(Long postId);
    boolean editComment(Long commentId, UserEntity user, Long postId, String content);
    boolean deleteComment(Long commentId);
}
