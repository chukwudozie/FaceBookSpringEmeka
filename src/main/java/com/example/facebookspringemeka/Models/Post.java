package com.example.facebookspringemeka.Models;

import lombok.Data;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq")
    @SequenceGenerator(name = "post_seq",sequenceName = "post_seq", allocationSize = 1)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "post_title", nullable = false, columnDefinition = "VARCHAR(45)")
    private String postTitle;

    @Column(name = "post_body", nullable = false, columnDefinition = "TEXT")
    private String postBody;

    @Column(name = "post_image_name", columnDefinition = "VARCHAR(45)")
    private String postImagename;

    @Column(name = "post_state", nullable = false)
    private String postState;  //existing post =>"ACTIVE". Deleted Post => "INACTIVE"

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
    private List<Comment> comments;


    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private PostLikes mylike;

}
