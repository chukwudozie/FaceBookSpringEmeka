package com.example.facebookspringemeka.DTO;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id; // Id of the Comment
    private Long postId; // Id of the Post with the comment
    private  Long userId;//Id of the User making the comment
    private String userName;// name of User making comment
    private String content; // content of the comment
    private String postTitle; // Title of the Post of the comment
    private String postImagename; // Image of the post with the comment

}
