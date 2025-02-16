package com.test.clush.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.clush.dto.SharedCalendarDto;
import com.clush.dto.ToDoDto;
import com.clush.entity.SharedCalendarEntity;
import com.clush.entity.UserEntity;
import com.clush.respository.SharedCalendarRepository;
import com.clush.respository.UserRepository;
import com.clush.service.SharedCalendarService;
import com.clush.util.UserUtil;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
class SharedCalendarServiceTest {

    @InjectMocks
    private SharedCalendarService sharedCalendarService;

    @Mock
    private SharedCalendarRepository sharedCalendarRepository;

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private HttpServletRequest request;

    @Test
    @DisplayName("공유 캘린더 조회 - 데이터 있음 (200)")
    void getSharedCalendarService_WhenDataExists_ReturnsOk() {
        // Given
        long mockUserId = 1L;
        List<SharedCalendarEntity> calendars = List.of(new SharedCalendarEntity());
        
        // Mocking UserUtil.getId()와 sharedCalendarRepository.findByUsersId() 메서드
        try (var mocked = mockStatic(UserUtil.class)) {
            mocked.when(UserUtil::getId).thenReturn(mockUserId);  // static 메서드 모킹
            given(sharedCalendarRepository.findByUsersId(mockUserId)).willReturn(calendars);

            // When
            ResponseEntity<List<SharedCalendarDto>> response = sharedCalendarService.getSharedCalendarService();

            // Then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().size()).isEqualTo(calendars.size());
        }
    }

    @Test
    @DisplayName("공유 캘린더 생성 (200)")
    void createSharedCalendarService_WhenValidData_ReturnsOk() {
        // Given
        String calendarName = "Test Calendar";
        long mockUserId = 1L;
        UserEntity mockUser = new UserEntity();
        mockUser.setId(mockUserId);
        
        // Mocking UserUtil.getId()와 userRepository.findById() 메서드
        try (var mocked = mockStatic(UserUtil.class)) {
            mocked.when(UserUtil::getId).thenReturn(mockUserId);  // static 메서드 모킹
            given(userRepository.findById(mockUserId)).willReturn(Optional.of(mockUser));

            // When
            ResponseEntity<Void> response = sharedCalendarService.createSharedCalendarService(calendarName);

            // Then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    @Test
    @DisplayName("공유할 할 일 생성 (200)")
    void createSharedToDoService_WhenValidData_ReturnsOk() {
        // Given
        long calendarId = 1L;
        long mockUserId = 1L;
        ToDoDto toDoDto = new ToDoDto();
        UserEntity mockUser = new UserEntity();
        SharedCalendarEntity mockCalendar = new SharedCalendarEntity();
        
        // Mocking UserUtil.getId(), userRepository.findById() 및 sharedCalendarRepository.findById() 메서드
        try (var mocked = mockStatic(UserUtil.class)) {
            mocked.when(UserUtil::getId).thenReturn(mockUserId);  // static 메서드 모킹
            given(userRepository.findById(mockUserId)).willReturn(Optional.of(mockUser));
            given(sharedCalendarRepository.findById(calendarId)).willReturn(Optional.of(mockCalendar));

            // When
            ResponseEntity<Void> response = sharedCalendarService.createSharedToDoService(calendarId, toDoDto);

            // Then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    @Test
    @DisplayName("공유 캘린더 조회 - 캘린더 존재하지 않음 (404)")
    void getSharedCalendarService_WhenCalendarNotFound_ReturnsNotFound() {
        // Given
        long calendarId = 1L;
        
        // Mocking sharedCalendarRepository.findById() 메서드
        given(sharedCalendarRepository.findById(calendarId)).willReturn(Optional.empty());

        // When
        ResponseEntity<SharedCalendarDto> response = sharedCalendarService.getSharedToDosService(calendarId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("캘린더 공유 (리다이렉트 처리)")
    void shareCalendarService_WhenAuthenticated_ReturnsRedirect() {
        // Given
        long calendarId = 1L;
        long mockUserId = 1L;
        UserEntity mockUser = new UserEntity();
        mockUser.setId(mockUserId);
        SharedCalendarEntity sharedCalendar = new SharedCalendarEntity();
        
        // HttpServletRequest 모킹
        StringBuffer requestURL = new StringBuffer("http://34.22.66.2/");
        given(request.getRequestURL()).willReturn(requestURL); // getRequestURL() 모킹

        // UserUtil.getId()와 userRepository.findById() 메서드 모킹
        try (var mocked = mockStatic(UserUtil.class)) {
            mocked.when(UserUtil::getId).thenReturn(mockUserId);  // static 메서드 모킹
            given(userRepository.findById(mockUserId)).willReturn(Optional.of(mockUser));
            given(sharedCalendarRepository.findById(calendarId)).willReturn(Optional.of(sharedCalendar));

            // When
            ResponseEntity<Void> response = sharedCalendarService.shareCalendarService(calendarId, request);

            // Then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
            assertThat(response.getHeaders().getLocation().toString()).contains("http://34.22.66.2/");
        }
    }
}
