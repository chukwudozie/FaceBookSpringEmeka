package com.example.facebookspringemeka.Repository;

import com.example.facebookspringemeka.Models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityByEmail(String UserEmail);
}