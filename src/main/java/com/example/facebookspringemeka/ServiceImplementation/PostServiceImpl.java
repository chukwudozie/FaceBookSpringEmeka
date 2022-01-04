package com.example.facebookspringemeka.ServiceImplementation;

import com.example.facebookspringemeka.DTO.PostDTO;
import com.example.facebookspringemeka.Models.Post;
import com.example.facebookspringemeka.Models.UserEntity;
import com.example.facebookspringemeka.Repository.PostRepository;
import com.example.facebookspringemeka.Repository.UserRepository;
import com.example.facebookspringemeka.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private boolean state;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<PostDTO> getAllPostByUser(UserEntity poster) {
        List<PostDTO> allPost = new ArrayList<>();
        List<Post> postsFromDB = postRepository.findAllByPostStateOrderByPostIdDesc("ACTIVE");

        for(Post eachPost: postsFromDB){

            PostDTO newPost = new PostDTO();
            newPost.setId(eachPost.getPostId());
            newPost.setPostTitle(eachPost.getPostTitle());
            newPost.setPostBody(eachPost.getPostBody());
            newPost.setPostImagename("/image/"+eachPost.getPostImagename());
            newPost.setPosterName(eachPost.getUser().getFirstname()+" "+eachPost.getUser().getSurname());

            allPost.add(newPost);
        }

        return allPost;
    }

    @Override
    public boolean createNewPost(Long userId, Post post) {
        UserEntity user = userRepository.findById(userId).get();

        if(user != null){
            System.out.println("there is a user to create post");
            post.setPostState("ACTIVE");
            System.out.println(post.getPostState());
            System.out.println(postRepository.save(post));
            postRepository.save(post);
            state = true;
        } else {
            System.out.println("there is no user");
            state = false;
        }
        return state;
    }

    @Override
    public boolean editPost(Long userId, Long postId, String postTitle, String postBody) {

        Post post = postRepository.findById(postId).get();
        if(post != null){
            System.out.println("there is a user for edit");
            post.setPostTitle(postTitle);
            post.setPostBody(postBody);
            System.out.println("post BOdy and title set");
            System.out.println(post);
            postRepository.save(post);
            System.out.println("Post successfully edit");
            return true;
        }
        System.out.println(" no post ");
        return false;
    }

    public boolean deletePost(Long postId, Long userId){


        Post post = postRepository.findPostByPostIdAndUserId(postId,userId);
        System.out.println("Getting to the service Impl");
        if(post != null){
            post.setPostState("INACTIVE");
            postRepository.save(post);
            System.out.println("post Successfully deleted");
            return  true;
        }
        System.out.println("Null post");
        return  false;
    }

    public List<Post> getPostByID(Long postId){
        List<Post> posts = new ArrayList<>();
        posts = postRepository.findPostByPostId(postId);

        System.out.println(" there is post");
            return  posts;


    }
}
