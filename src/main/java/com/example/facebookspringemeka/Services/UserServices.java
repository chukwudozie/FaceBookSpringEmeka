package com.example.facebookspringemeka.Services;

import com.example.facebookspringemeka.Models.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserServices {

    boolean registerNewUser(UserEntity newUser);
    UserEntity getRegisteredUser(String email, String Password);

}
