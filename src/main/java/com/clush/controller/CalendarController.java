package com.clush.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clush.entity.CalendarEntity;
import com.clush.service.CalendarService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

	@Autowired
	private CalendarService calendarService;
	
	@GetMapping("/{yearMonth}")
	@Operation(summary = "개인 캘린더 조회 API", description = "사용자에게 특정 년, 월에 해당하는 todo 배열 데이터 반환")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "성공적으로 todo 배열 데이터를 반환"),
	        @ApiResponse(responseCode = "204", description = "데이터 없음"),
	        @ApiResponse(responseCode = "404", description = "일정 데이터 중 todos가 없거나 잘못된 요청이 있을 경우")
	    })
	public ResponseEntity<List<CalendarEntity>> getCalendar(@PathVariable("yearMonth") String yearMonth){
		return calendarService.getCalendarService(yearMonth);
	}
}
