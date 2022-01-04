package com.example.facebookspringemeka.ServiceImplementation;

import com.example.facebookspringemeka.Models.UserEntity;
import com.example.facebookspringemeka.Repository.UserRepository;
import com.example.facebookspringemeka.Resources.PasswordEncrypt;
import com.example.facebookspringemeka.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServices {
   private UserRepository userRepository;
   private boolean state;

   @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean registerNewUser(UserEntity newUser) {
       newUser.setPassword(PasswordEncrypt.encryptPassword(newUser.getPassword()));
       UserEntity user = userRepository.findUserEntityByEmail(newUser.getEmail());
       if(user == null){
           state = true;
           userRepository.save(newUser);
           System.out.println("User Successfully Added to Db");
       } else {
           state = false;
           System.out.println("User not saved!!");
       }
        return state;
    }

    @Override
    public UserEntity getRegisteredUser(String email, String password) {

       UserEntity newUser = null;
       try{
        newUser = userRepository.findUserEntityByEmail(email);
       if(!password.equals(PasswordEncrypt.decryptPassword(newUser.getPassword()))){
           newUser = null;
       }
    }catch (Exception e){
       e.printStackTrace();
       }
       return newUser;
    }
}
