package com.clush.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clush.dto.ToDoDto;
import com.clush.entity.ToDoEntity;
import com.clush.service.ToDoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("to-do")
public class ToDoController {
    
    @Autowired
    private ToDoService toDoService;
    
    @GetMapping
    @Operation(summary = "오늘 todo 모두 조회 API", 
               description = "오늘 날짜에 해당하는 Todo 작업을 모두 가져옵니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 오늘의 todo 데이터를 반환"),
        @ApiResponse(responseCode = "204", description = "오늘의 todo 데이터가 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<List<ToDoEntity>> getTodayToDos(){
        return toDoService.getTodayToDoService();
    }
    
    @GetMapping("/{date}/date")
    public ResponseEntity<List<ToDoEntity>> getSomedayToDos(@PathVariable("date") String date){
    	return toDoService.getSomedayToDoService(date);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "특정 todo 조회 API", 
               description = "특정 id에 해당하는 Todo 작업 하나를 가져옵니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 특정 todo 데이터를 반환"),
        @ApiResponse(responseCode = "404", description = "해당 id의 todo 작업이 존재하지 않음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<ToDoEntity> getToDo(@PathVariable("id") Long id){
        return toDoService.getToDoService(id);
    }
    
    @PostMapping
    @Operation(summary = "todo 생성 API", 
               description = "todo 작업을 생성합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공적으로 todo 작업이 생성됨"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Void> saveToDo(@RequestBody ToDoDto toDoDto){
        System.out.println(toDoDto);
        return toDoService.saveToDoService(toDoDto);
    }
    
    @PatchMapping("/{id}")
    @Operation(summary = "진행 상태 변경 API", 
               description = "특정 todo의 진행상태를 '진행중' 또는 '완료'로 변경합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 진행 상태가 변경됨"),
        @ApiResponse(responseCode = "404", description = "해당 id의 todo 작업이 존재하지 않음"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Void> modifyStatus(@PathVariable("id") Long id, @RequestBody String status){
        System.out.println(status);
        return toDoService.modifyStatusService(id, status);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "특정 todo 수정 API", 
               description = "특정 id의 todo를 수정합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 todo 작업이 수정됨"),
        @ApiResponse(responseCode = "404", description = "해당 id의 todo 작업이 존재하지 않음"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Void> modifyToDo(@PathVariable("id") Long id, @RequestBody ToDoDto toDoDto){
        return toDoService.modifyToDoService(id, toDoDto);
    }
}
