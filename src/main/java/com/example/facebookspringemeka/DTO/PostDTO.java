package com.example.facebookspringemeka.DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PostDTO {
    private Long id;
    private String postTitle;
    private String postBody;
    private String postImagename;
    private boolean liked;
    private int likeCount;
    private int commentCount;
    private String posterName;
    private String posterEmail;
    private Long userId;
}
