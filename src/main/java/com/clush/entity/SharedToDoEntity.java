package com.clush.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.clush.dto.ToDoDto;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class SharedToDoEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String title; 
    
    private String description; 
    
    private String status;
    
    private int priority;
    
    private String date; 
    
    private LocalDateTime createdAt; 
    
    private LocalDateTime updatedAt;
   
    @ManyToOne
    private SharedCalendarEntity sharedCalendar; 
    
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user; 
    
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now(); 
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now(); 
    }
    
    public SharedToDoEntity(ToDoDto toDoDto) {
    	this.title = toDoDto.getTitle();
    	this.description = toDoDto.getDescription();
    	this.status = "진행중";
    	this.priority = toDoDto.getPriority();
    	this.date = toDoDto.getDate();
    
    }
}
