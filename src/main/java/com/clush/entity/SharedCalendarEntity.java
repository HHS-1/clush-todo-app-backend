package com.clush.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class SharedCalendarEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	 
	@ManyToMany
	@JoinTable(
	 name = "calendar_user", 
	 joinColumns = @JoinColumn(name = "calendar_id"), 
	 inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<UserEntity> users;  // 이 캘린더를 공유하는 사용자들
	
	@OneToMany(mappedBy = "sharedCalendar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SharedToDoEntity> sharedTodos; // 이 캘린더에 속한 이벤트들
	
}
