package com.example.messageuserapp.repository;

import com.example.messageuserapp.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel,Long> {
    Optional<UserModel> findByLoginAndPassword(String login, String password);

}
