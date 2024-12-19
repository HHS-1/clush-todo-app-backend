package com.clush.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clush.dto.SharedCalendarDto;
import com.clush.dto.ToDoDto;
import com.clush.entity.SharedCalendarEntity;
import com.clush.service.SharedCalendarService;
import com.clush.util.AESUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("shared")
public class SharedCalendarController {
    
    @Autowired
    private SharedCalendarService sharedCalendarService;
    
    @GetMapping
    @Operation(summary = "공유 캘린더 목록 조회 API", 
               description = "현재 사용자의 모든 공유 캘린더를 가져옵니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 공유 캘린더 데이터를 반환"),
        @ApiResponse(responseCode = "204", description = "공유 캘린더 데이터가 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<List<SharedCalendarDto>> getSharedCalendar(){
        return sharedCalendarService.getSharedCalendarService();
    }
    
    @GetMapping("{id}")
    @Operation(summary = "공유 작업 조회 API", 
               description = "현재 사용자가 선택한 공유 캘린더의 공유 작업물을 가져옵니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 공유 작업 데이터를 반환"),
        @ApiResponse(responseCode = "404", description = "선택한 공유 캘린더가 존재하지 않음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<SharedCalendarDto> getSharedToDos(@PathVariable("id") long id){
        return sharedCalendarService.getSharedToDosService(id);
    }
    
    @PostMapping
    @Operation(summary = "공유 캘린더 생성 API", 
               description = "공유 캘린더를 생성합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "공유 캘린더가 성공적으로 생성됨"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Void> createSharedCalendar(@RequestBody String name){
        return sharedCalendarService.createSharedCalendarService(name);
    }
    
    @PostMapping("{id}")
    @Operation(summary = "공유 작업 생성 API", 
               description = "선택된 공유 캘린더에서 공유 작업을 생성합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "공유 작업이 성공적으로 생성됨"),
        @ApiResponse(responseCode = "404", description = "선택된 공유 캘린더가 존재하지 않음"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Void> createSharedToDo(@RequestBody ToDoDto toDoDto,@PathVariable("id") long id){
        return sharedCalendarService.createSharedToDoService(id,toDoDto);
    }
    
    @GetMapping("invite")
    @Operation(summary = "공유 캘린더 초대 수락 API", 
               description = "다른 사용자의 공유 캘린더 초대를 수락하고 자신의 공유 캘린더 목록에 추가합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "초대 수락 성공, 공유 캘린더에 추가됨"),
        @ApiResponse(responseCode = "302", description = "초대 받은 사용자가 비로그인 상태, 로그인 페이지로 전환"),
        @ApiResponse(responseCode = "400", description = "잘못된 초대 코드"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Void> shareCalendar(@RequestParam("accept") String accept, HttpServletRequest request) throws Exception{
        return sharedCalendarService.shareCalendarService(Long.valueOf(AESUtil.decrypt(accept)), request);
    }
}
