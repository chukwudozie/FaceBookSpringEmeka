package com.example.facebookspringemeka.Services;

import com.example.facebookspringemeka.Models.UserEntity;

public interface LikeService {
    boolean likeAPost(UserEntity user, Long postId, String like);
}
