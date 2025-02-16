package com.test.clush.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.clush.BackendApplication;
import com.clush.entity.CalendarEntity;
import com.clush.respository.CalendarRepository;
import com.clush.service.CalendarService;

@SpringBootTest(classes = BackendApplication.class)
@ExtendWith(MockitoExtension.class)
class CalendarServiceTest {

    @InjectMocks
    private CalendarService calendarService;

    @Mock
    private CalendarRepository calendarRepository;

    @Test
    @DisplayName("개인 캘린더 조회 - 데이터 있음 (200)")
    void getCalendar_WhenDataExists_ReturnsOk() {
        // Given
        String yearMonth = "2025-02";
        long mockUserId = 2L;  // 임의로 1L의 사용자 ID 설정
        List<CalendarEntity> calendarList = List.of(new CalendarEntity());

        // calendarService.getCalendarService() 메서드에서 userId를 직접 설정
        given(calendarRepository.findByUserIdAndDateStartingWith(mockUserId, yearMonth))
                .willReturn(calendarList);

        // When
        ResponseEntity<List<CalendarEntity>> response = calendarService.getCalendarService(yearMonth); // ID를 메서드 인자로 넘기기

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(calendarList.size());
    }
}
