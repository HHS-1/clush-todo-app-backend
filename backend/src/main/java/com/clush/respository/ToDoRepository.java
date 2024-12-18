package com.clush.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clush.entity.ToDoEntity;

public interface ToDoRepository extends JpaRepository<ToDoEntity, Long> {
    List<ToDoEntity> findByDate(String date);
    
    List<ToDoEntity> findByDateAndUserId(String date, Long userId);
}
