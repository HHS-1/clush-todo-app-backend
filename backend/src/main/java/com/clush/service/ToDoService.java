package com.clush.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clush.dto.ToDoDto;
import com.clush.entity.CalendarEntity;
import com.clush.entity.ToDoEntity;
import com.clush.respository.CalendarRepository;
import com.clush.respository.ToDoRepository;

@Service
public class ToDoService {

	@Autowired
	private ToDoRepository toDoRepository;
	@Autowired
	private CalendarRepository calendarRepository;
	
	@Transactional(readOnly = true)
	public ResponseEntity<List<ToDoEntity>> getTodayToDoService() {
        
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(date);

        // 오늘 날짜에 해당하는 ToDo 조회
        List<ToDoEntity> todayToDos = toDoRepository.findByDate(today);

        if (todayToDos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(todayToDos);
    }
	
	@Transactional
	public ResponseEntity<Void> saveToDoService(ToDoDto toDoDto){
		
		//추가된 작업의 날짜에 해당하는 캘린더 Entity 호출
	    CalendarEntity calendarEntity = calendarRepository.findByDate(toDoDto.getDate());
	    
	    if (calendarEntity == null) { // 없으면 추가

	        calendarEntity = new CalendarEntity();
	        calendarEntity.setDate(toDoDto.getDate());
	    }
	    
	    ToDoEntity toDoEntity = new ToDoEntity(toDoDto);
	    	   
	    toDoEntity.setCalendar(calendarEntity); 
	    calendarEntity.getTodos().add(toDoEntity); 
	    
	    calendarRepository.save(calendarEntity);
		
		return ResponseEntity.ok().build();
	}
	
	public ResponseEntity<Void> modifyStatusService(Long id, String status){
		Optional<ToDoEntity> todo = toDoRepository.findById(id);
		
		if (todo.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }

	    ToDoEntity toDoEntity = todo.get();
	    
	    toDoEntity.changeStatus(status);
	    
	    toDoRepository.save(toDoEntity);
		 
		return ResponseEntity.ok().build();
		
	}
	
}
