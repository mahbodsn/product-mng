package com.tangerine.productmng.api;

import com.tangerine.productmng.api.dto.ExceptionResponse;
import com.tangerine.productmng.exception.BusinessException;
import com.tangerine.productmng.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public final ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException ex, WebRequest request) {
        logger.error("received business exception -> " + ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ExceptionResponse(ex.getMessage(), ex.getCode()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        logger.error("received business exception -> " + ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(ErrorCode.AUTH_INVALID_CREDENTIAL.getMessage(), ErrorCode.AUTH_INVALID_CREDENTIAL.getCode()));
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleUnknownException(Exception exception, WebRequest request) throws Exception {
        logger.error("received unhandled exception -> " + exception);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ExceptionResponse(ErrorCode.FAILURE.getMessage(), ErrorCode.FAILURE.getCode()));
    }

}
