package com.clush.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clush.entity.CalendarEntity;
import com.clush.respository.CalendarRepository;

@Service
public class CalendarService {
	
	@Autowired
	private CalendarRepository calendarRepository;
	
	public ResponseEntity<List<CalendarEntity>> getCalendarService(String yearMonth){
		List<CalendarEntity> calendarData = calendarRepository.findByDateStartingWith(yearMonth);
	    
	    if (calendarData.isEmpty()) {
	        return ResponseEntity.noContent().build(); 
	    }
	    
	    for (CalendarEntity calendar : calendarData) {
	        if (calendar.getTodos() == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
	        }
	    }
		
		return ResponseEntity.ok(calendarData);
	}
}
