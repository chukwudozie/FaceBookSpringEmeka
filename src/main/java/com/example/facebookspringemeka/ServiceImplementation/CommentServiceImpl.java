package com.example.facebookspringemeka.ServiceImplementation;

import com.example.facebookspringemeka.DTO.CommentDTO;
import com.example.facebookspringemeka.Models.Comment;
import com.example.facebookspringemeka.Models.Post;
import com.example.facebookspringemeka.Models.UserEntity;
import com.example.facebookspringemeka.Repository.CommentRepository;
import com.example.facebookspringemeka.Repository.PostRepository;
import com.example.facebookspringemeka.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public boolean createNewComment(Long userId, Long postId, Comment comment) {
        System.out.println("in the comment Impl");
        Post post = postRepository.findById(postId).get();
        if(post != null) {
            System.out.println("From Comment Impl: Comment saved Successfully");
            comment.setPost(post);
            commentRepository.save(comment);
            return true;
        }
        System.out.println("From Comment Impl: Comment not saved");
        return false;
    }

    @Override
    public List<CommentDTO> getAllCommentsForPost(Long postId){

        System.out.println("Getting all comments for a post");
        List<CommentDTO> allComments = new ArrayList<>();

        List<Comment> commentsFromDb = commentRepository.findAllByPostPostId(postId);
        for(Comment eachComment: commentsFromDb){
            CommentDTO currentComment = new CommentDTO();
            currentComment.setId((eachComment.getId()));
            currentComment.setPostId(eachComment.getPost().getPostId());
            currentComment.setContent(eachComment.getContent());
            currentComment.setUserName("Made By: "+eachComment.getUser().getSurname()+" "+eachComment.getUser().getFirstname());
            currentComment.setPostTitle(eachComment.getPost().getPostTitle());
            currentComment.setPostImagename("/image/"+eachComment.getPost().getPostImagename());
            currentComment.setUserId(eachComment.getUser().getId());

            allComments.add(currentComment);
        }
        System.out.println("comment List Created");
        return allComments;

    }

    @Override
    public boolean editComment(Long commentId, UserEntity user, Long postId, String content) {
         Post currentPost = postRepository.findById(postId).get();
         Comment commentFromDb = commentRepository.findCommentById(commentId);
         if(commentFromDb != null) {
             commentFromDb.setContent(content);
             commentFromDb.setUser(user);
             commentFromDb.setPost(currentPost);
             commentRepository.save(commentFromDb);
             System.out.println("Comment successfully edited");
             return true;
         }
        return false;
    }

    @Override
    public boolean deleteComment(Long commentId) {
//       Comment comment = commentRepository.findCommentById(commentId);
//       if(comment != null){
           commentRepository.deleteCommentById(commentId);
           System.out.println("Comment deleted");
           return true;
//       }
//        System.out.println("Comment Not deleted");
    }

}
