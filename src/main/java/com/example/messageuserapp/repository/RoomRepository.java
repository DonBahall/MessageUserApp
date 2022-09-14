package com.example.messageuserapp.repository;

import com.example.messageuserapp.Model.RoomModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomModel,Long> {
}
