package com.test.clush.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.clush.controller.SharedCalendarController;
import com.clush.dto.SharedCalendarDto;
import com.clush.service.SharedCalendarService;


@WebMvcTest(SharedCalendarController.class)
public class SharedCalendarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SharedCalendarService sharedCalendarService;

    @InjectMocks
    private SharedCalendarController sharedCalendarController;

    private List<SharedCalendarDto> sharedCalendarDtos;

    @BeforeEach
    public void setUp() {
        // 테스트에 필요한 데이터를 설정합니다.
        SharedCalendarDto calendar1 = new SharedCalendarDto(1L, "Calendar 1");
        SharedCalendarDto calendar2 = new SharedCalendarDto(2L, "Calendar 2");
        sharedCalendarDtos = Arrays.asList(calendar1, calendar2);
    }

    @Test
    public void testGetSharedCalendar() throws Exception {
        // ResponseEntity<List<SharedCalendarDto>>를 반환하도록 모의합니다.
        when(sharedCalendarService.getSharedCalendarService()).thenReturn(ResponseEntity.ok(sharedCalendarDtos));

        // GET 요청을 보내고, 응답이 200 OK인지 확인합니다.
        mockMvc.perform(get("/shared"))
                .andExpect(status().isOk()) // 200 OK 응답 확인
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // 응답 콘텐츠 타입 확인
                .andExpect(jsonPath("$[0].name").value("Calendar 1")) // 첫 번째 캘린더의 이름 확인
                .andExpect(jsonPath("$[1].name").value("Calendar 2")); // 두 번째 캘린더의 이름 확인
    }

    @Test
    public void testGetSharedCalendarNoContent() throws Exception {
        when(sharedCalendarService.getSharedCalendarService()).thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(get("/shared"))
                .andExpect(status().isNoContent()); 
    }
}
