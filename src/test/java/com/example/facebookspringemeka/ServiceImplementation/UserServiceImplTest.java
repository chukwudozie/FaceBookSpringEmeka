package com.example.facebookspringemeka.ServiceImplementation;

import com.example.facebookspringemeka.Models.UserEntity;
import com.example.facebookspringemeka.Repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Repository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private  UserEntity user;


    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setFirstname("Emeka");
        user.setSurname("Chuks");
        user.setPassword("1234");
        user.setEmail("mekus@gmail.com");
        user.setGender("Male");
        user.setDob("1990-12-12");

    }

    @Test
    void registerNewUser() {

//        Mock User Repository
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

//        Call the method to be tested
       boolean state =  userService.registerNewUser(user);

//        Assertions
        Assertions.assertTrue(true, String.valueOf(state));
        verify(userRepository,times(1)).save(any(UserEntity.class));
    }



    @Test
    void getRegisteredUser(){
//        mock User Repository
        when(userRepository.findUserEntityByEmail(anyString())).thenReturn(user);

//        Call Method to be Tested
        UserEntity newUser = userRepository.findUserEntityByEmail("mail@mail.com");

        userService.getRegisteredUser(user.getEmail(), user.getPassword());
        assertNotNull(newUser);
        assertEquals("mekus@gmail.com",newUser.getEmail());

    }
}