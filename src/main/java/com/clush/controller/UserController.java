package com.clush.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clush.dto.UserDto;
import com.clush.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "사용자 정보의 유효성을 검사하고 토큰을 추가합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 로그인됨"),
        @ApiResponse(responseCode = "401", description = "잘못된 사용자 정보"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Void> login(@RequestBody UserDto userDto, HttpServletResponse res, HttpServletRequest req){
        return userService.loginService(userDto, res, req);
    }
    
    @PostMapping("sign-up")
    @Operation(summary = "회원가입 API", description = "사용자의 정보를 데이터베이스에 저장합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "회원가입 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 회원가입 요청"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Void> signUp(@RequestBody UserDto userDto){
        return userService.signUpService(userDto);
    }
    
    @GetMapping("check")
    @Operation(summary = "로그인 상태 점검 API", description = "사용자의 로그인 여부를 검사하여 클라이언트로 전송합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자가 로그인 상태임"),
        @ApiResponse(responseCode = "401", description = "사용자가 로그인 상태가 아님"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Void> checkLogin(HttpServletRequest req){
        return userService.checkLoginService(req);
    }
    
    @GetMapping("logout")
    @Operation(summary = "로그아웃 API", description = "사용자의 토큰을 쿠키와 redis에서 제거합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 로그아웃됨"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public void logout(HttpServletResponse res) {
        userService.logoutService(res);
    }
}
