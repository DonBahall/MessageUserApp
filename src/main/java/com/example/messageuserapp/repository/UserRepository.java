package com.example.messageuserapp.repository;

import com.example.messageuserapp.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long> {
    Optional<UserModel> findByUsernameAndPassword(String username, String password);
}
