package com.example.facebookspringemeka.Repository;

import com.example.facebookspringemeka.Models.Post;
import com.example.facebookspringemeka.Models.PostLikes;
import com.example.facebookspringemeka.Models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {
    List<PostLikes> findAllByPostPostId(Long postId);
    List<PostLikes> findAllByPostPostIdAndAndUserId(Long postId, Long userId);
    @Transactional
    void deletePostLikesByPostAndUser(Post post, UserEntity user);



}
