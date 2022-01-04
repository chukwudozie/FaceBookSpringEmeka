package com.example.facebookspringemeka.ServiceImplementation;

import com.example.facebookspringemeka.Models.Post;
import com.example.facebookspringemeka.Models.PostLikes;
import com.example.facebookspringemeka.Models.UserEntity;
import com.example.facebookspringemeka.Repository.PostLikesRepository;
import com.example.facebookspringemeka.Repository.PostRepository;
import com.example.facebookspringemeka.Services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    private PostRepository postRepository;
    private PostLikesRepository likesRepository;

    @Autowired
    public LikeServiceImpl(PostRepository postRepository, PostLikesRepository likesRepository) {
        this.postRepository = postRepository;
        this.likesRepository = likesRepository;

    }

    @Override
    public boolean likeAPost(UserEntity user, Long postId, String likeAction) {
        boolean likeState = false;
        try{
            Post post = postRepository.findById(postId).get();
            PostLikes likes = new PostLikes();
            likes.setUser(user);
            likes.setPost(post);
            if(likeAction.equals("1")){
                likesRepository.save(likes);
                System.out.println("Liked!");

            } else {
                likesRepository.deletePostLikesByPostAndUser(post,user);
                System.out.println("unliked");
            }
            likeState = true;

        }catch (Exception e){
            e.printStackTrace();
        }
      return likeState;
    }
}
