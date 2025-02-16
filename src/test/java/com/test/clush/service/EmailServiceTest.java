package com.test.clush.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.clush.entity.UserEntity;
import com.clush.respository.UserRepository;
import com.clush.service.EmailService;
import com.clush.util.AESUtil;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AESUtil aesUtil;

    @Mock
    private Transport transport;  // 메일 전송을 모킹하기 위해 사용

    private String testEmail = "test@example.com";
    private String testUserId = "testUserId";

    @BeforeEach
    void setup() {
        // 기본적인 셋업 (필요한 값들)
    }

    @Test
    void sendEmailService_WhenUserExists_ReturnsOk() throws Exception {
        // Given
        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setUserId(testEmail);

        given(userRepository.findByUserId(testEmail)).willReturn(mockUserEntity);

        // ✅ static 메서드 모킹
        try (MockedStatic<AESUtil> mockedStatic = Mockito.mockStatic(AESUtil.class)) {
            mockedStatic.when(() -> AESUtil.encrypt(testUserId)).thenReturn("encryptedId");

            // When
            ResponseEntity<Void> response = emailService.sendEmailService(testEmail, testUserId);

            // Then
            assertThat(response.getStatusCode().value()).isEqualTo(200);
            verify(userRepository, times(1)).findByUserId(testEmail);
        }
    }

    @Test
    void sendEmailService_WhenUserNotFound_ReturnsNotFound() throws Exception {
        // Given
        given(userRepository.findByUserId(testEmail)).willReturn(null);

        // When
        ResponseEntity<Void> response = emailService.sendEmailService(testEmail, testUserId);

        // Then
        assertThat(response.getStatusCode().value()).isEqualTo(404);  // 사용자 없으면 404 응답
    }

    @Test
    void sendEmailService_WhenMessagingExceptionOccurs_ReturnsInternalServerError() throws Exception {
        // Given
        String testEmail = "test@example.com";
        String testUserId = "testUserId";
        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setUserId(testEmail);

        given(userRepository.findByUserId(testEmail)).willReturn(mockUserEntity);
        given(AESUtil.encrypt(testUserId)).willReturn("encryptedId");

        try (MockedStatic<Transport> mockedTransport = Mockito.mockStatic(Transport.class)) {

            mockedTransport.when(() -> Transport.send(any(Message.class), any(Address[].class)))
                           .thenThrow(new MessagingException("Test exception"));

            // When
            ResponseEntity<Void> response = emailService.sendEmailService(testEmail, testUserId);

            // Then
            assertThat(response.getStatusCode().value()).isEqualTo(500);  
        }
    }

}
