package com.clush.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clush.service.EmailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("email")
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping("{id}")
	@Operation(summary = "이메일 전송 API", description = "캘린더 공유를 위한 공유 초대 이메일 전송")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "성공적으로 공유 캘린더 초대 이메일 발송"),
	        @ApiResponse(responseCode = "404", description = "해당 이메일을 가진 사용자가 없음")
	    })
	public ResponseEntity<Void> sendEmail(@RequestBody String email, @PathVariable("id") String id) throws Exception{
		return emailService.sendEmailService(email, id);
	}
}
