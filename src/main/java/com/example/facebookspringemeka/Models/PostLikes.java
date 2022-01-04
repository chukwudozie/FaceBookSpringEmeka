package com.example.facebookspringemeka.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PostLikes {

    @Id
    @SequenceGenerator(
            name = "likes_sequence",
            sequenceName = "likes_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy  = SEQUENCE,
            generator = "likes_sequence"
    )
    private Long id;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(referencedColumnName = "post_id")
    private Post post;
}
