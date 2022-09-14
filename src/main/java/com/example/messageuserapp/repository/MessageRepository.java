package com.example.messageuserapp.repository;

import com.example.messageuserapp.Model.CustomMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<CustomMessage,Long> {
}
