package com.example.facebookspringemeka.Models;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "user")
@Table(name = "user_Entity",  uniqueConstraints = {
        @UniqueConstraint(name = "user_email_constraint", columnNames = "email")
})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName ="user_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name", nullable = false, columnDefinition = "VARCHAR(45)")
    private String firstname;
    @Column(name = "surname", nullable = false, columnDefinition = "VARCHAR(45)")
    private String surname;
    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(45)")
    private String email;
    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(45)")
    private String password;
    @Column(name = "gender", nullable = false, columnDefinition = "VARCHAR(45)")
    private String gender;
    @Column(name = "date_of_birth", nullable = false, columnDefinition = "VARCHAR(45)")
    private String dob;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List <Post> posts;

    @OneToOne(mappedBy = "user")
    private PostLikes myLikes;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    //
//    @Override
//    public String toString() {
//        return "UserEntity{" +
//                "Id=" + Id +
//                ", firstname='" + firstname + '\'' +
//                ", surname='" + surname + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                ", gender='" + gender + '\'' +
//                ", dob='" + dob + '\'' +
//                ", posts=" + posts +
//                '}';
//    }
}
