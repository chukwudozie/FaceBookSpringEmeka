package com.example.facebookspringemeka.Services;

import com.example.facebookspringemeka.DTO.PostDTO;
import com.example.facebookspringemeka.Models.Post;
import com.example.facebookspringemeka.Models.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    List<PostDTO> getAllPostByUser(UserEntity poster);
    boolean createNewPost(Long userId, Post post);
    boolean editPost(Long userId, Long postId, String postTitle, String postBody);
//    boolean deletePost(Long postId, Long userId);
}
