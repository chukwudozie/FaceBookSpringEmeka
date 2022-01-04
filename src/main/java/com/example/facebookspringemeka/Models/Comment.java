package com.example.facebookspringemeka.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Comment {

     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
     @SequenceGenerator(name = "comment_sequence",sequenceName = "comment_sequence", allocationSize = 1)
     @Column(name = "id")
     private Long id;

     @Column(name = "content", nullable = false, columnDefinition = "TEXT")
     private String content;

     @ManyToOne
     @JoinColumn(name = "user_id", referencedColumnName = "id")
     private UserEntity user;

     @ManyToOne
     @JoinColumn(name = "post_id", referencedColumnName = "post_id")
     private Post post;



}
