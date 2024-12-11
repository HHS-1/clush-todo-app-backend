package com.clush.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clush.dto.ToDoDto;
import com.clush.entity.ToDoEntity;
import com.clush.service.ToDoService;

@RestController
@RequestMapping("to-do")
public class ToDoController {
	
	@Autowired
	private ToDoService toDoService;
	
	@GetMapping
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<List<ToDoEntity>> getTodayToDo(){
		return toDoService.getTodayToDoService();
	}
	
	@PostMapping
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<Void> saveToDo(@RequestBody ToDoDto toDoDto){
		return toDoService.saveToDoService(toDoDto);
	}
	
	@PatchMapping("/{id}")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<Void> modifyStatus(@PathVariable("id") Long id, @RequestBody String status){
		System.out.println(status);
		return toDoService.modifyStatusService(id, status);
	}
}