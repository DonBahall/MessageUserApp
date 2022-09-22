package com.example.messageuserapp.repository;

import com.example.messageuserapp.Model.CustomMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<CustomMessage,Long> {
    List<CustomMessage> findAllByNumOfRoom(Long numOfRoom);
}
