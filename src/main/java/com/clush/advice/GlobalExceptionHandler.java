package com.clush.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

//@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private String getErrorLocation(Exception e) {
        StackTraceElement element = e.getStackTrace()[0]; // 가장 첫 번째 스택 트레이스
        return String.format("%s.%s(%s:%d)", 
                             element.getClassName(), // 클래스명
                             element.getMethodName(), // 메서드명
                             element.getFileName(), // 파일명
                             element.getLineNumber()); // 라인 번호
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonParseError(HttpMessageNotReadableException e) {
        logger.error("JsonParseError: 잘못된 요청 데이터 형식입니다. at {}", getErrorLocation(e), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body("잘못된 요청 데이터 형식입니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e) {
        StringBuilder errors = new StringBuilder("유효성 검증에 실패했습니다: ");
        e.getBindingResult().getFieldErrors().forEach(error ->
            errors.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ")
        );
        logger.warn("ValidationException: {} at {}", errors, getErrorLocation(e), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        logger.warn("MethodNotAllowed: 허용되지 않은 HTTP 메서드입니다. at {}", getErrorLocation(e), e);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                             .body("허용되지 않은 HTTP 메서드입니다.");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<String> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException e) {
        logger.warn("MediaTypeNotSupported: 지원되지 않는 Content-Type입니다. at {}", getErrorLocation(e), e);
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                             .body("지원되지 않는 Content-Type입니다.");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException e) {
        logger.info("EntityNotFound: 요청한 데이터를 찾을 수 없습니다. at {}", getErrorLocation(e), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("요청한 데이터를 찾을 수 없습니다.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        logger.warn("IllegalArgumentException: 잘못된 요청 값: {} at {}", e.getMessage(), getErrorLocation(e), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body("잘못된 요청 값: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {
        logger.error("GeneralException: 서버에서 처리 중 오류가 발생했습니다. at {}", getErrorLocation(e), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("서버에서 처리 중 오류가 발생했습니다.");
    }
}
