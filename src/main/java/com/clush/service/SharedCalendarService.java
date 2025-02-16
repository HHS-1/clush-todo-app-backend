package com.clush.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.clush.dto.SharedCalendarDto;
import com.clush.dto.ToDoDto;
import com.clush.entity.SharedCalendarEntity;
import com.clush.entity.SharedToDoEntity;
import com.clush.entity.UserEntity;
import com.clush.respository.SharedCalendarRepository;
import com.clush.respository.UserRepository;
import com.clush.util.UserUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class SharedCalendarService {
	
	@Autowired
	private SharedCalendarRepository sharedCalendarRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public ResponseEntity<List<SharedCalendarDto>> getSharedCalendarService(){
		long userId = UserUtil.getId(); 

	    List<SharedCalendarEntity> calendars = sharedCalendarRepository.findByUsersId(userId);

	    List<SharedCalendarDto> calendarDtos = calendars.stream()
	        .map(calendar -> new SharedCalendarDto(calendar)) 
	        .collect(Collectors.toList());

	    return ResponseEntity.ok(calendarDtos);
	}
	
	@Transactional
	public ResponseEntity<Void> createSharedCalendarService(String name){
		long userId = UserUtil.getId();
		UserEntity user = userRepository.findById(userId)
	            .orElseThrow(() -> new IllegalArgumentException("아이디를 찾을 수 없음 id: " + userId));
	    
		SharedCalendarEntity sharedCalendarEntity = new SharedCalendarEntity();
		sharedCalendarEntity.setName(name);

		List<UserEntity> users = new ArrayList<>();
	    users.add(user);
	    sharedCalendarEntity.setUsers(users); // 사용자 목록 설정
		
		sharedCalendarRepository.save(sharedCalendarEntity);
		return ResponseEntity.ok().build();
	}
	
	@Transactional
	public ResponseEntity<Void> createSharedToDoService(long id, ToDoDto toDoDto){
	    long userId = UserUtil.getId();
	    UserEntity user = userRepository.findById(userId)
	            .orElseThrow(() -> new IllegalArgumentException("아이디를 찾을 수 없음 id: " + userId));
	    
	    SharedCalendarEntity sharedCalendar = sharedCalendarRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("공유 캘린더를 찾을 수 없음 id: " + id));
	    
	    if (!sharedCalendar.getUsers().contains(user)) {
	        sharedCalendar.getUsers().add(user);
	    }
	    sharedCalendarRepository.save(sharedCalendar);
	    
	    SharedToDoEntity sharedToDoEntity = new SharedToDoEntity(toDoDto);
	    sharedToDoEntity.setUser(user);
	    sharedToDoEntity.setSharedCalendar(sharedCalendar);
	    
	    sharedCalendar.getSharedTodos().add(sharedToDoEntity);
	    sharedCalendarRepository.save(sharedCalendar);

	    return ResponseEntity.ok().build();
	}
	
	@Transactional
	public ResponseEntity<SharedCalendarDto> getSharedToDosService(long id){
		SharedCalendarEntity sharedCalendar = sharedCalendarRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("공유 캘린더를 찾을 수 없음 id: " + id));

		SharedCalendarDto dto = new SharedCalendarDto(sharedCalendar);
		
		return ResponseEntity.ok(dto);
	}
	
	@Transactional
	public ResponseEntity<Void> shareCalendarService(long id, HttpServletRequest request){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증되지 않은 경우
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
        	HttpSession session = request.getSession();
            session.setAttribute("redirectUrl", request.getRequestURL().toString());
        	
            return ResponseEntity.status(302)
                .header("Location", "http://34.22.66.2/login")
                .build();
        }
        	
        long userId = UserUtil.getId();
        UserEntity user = userRepository.findById(userId)
	            .orElseThrow(() -> new IllegalArgumentException("아이디를 찾을 수 없음 id: " + userId));
	    
        SharedCalendarEntity sharedCalendar = sharedCalendarRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("공유 캘린더를 찾을 수 없음 id: " + id));

        sharedCalendar.getUsers().add(user);

        return ResponseEntity.status(302)
                .header("Location", "http://34.22.66.2/")
                .build();
	
	}

}
