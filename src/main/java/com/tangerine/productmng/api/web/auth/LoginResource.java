package com.tangerine.productmng.api.web.auth;

import com.tangerine.productmng.api.web.auth.dto.LoginRequest;
import com.tangerine.productmng.api.web.auth.dto.LoginResponse;
import com.tangerine.productmng.api.web.auth.mapper.LoginServiceMapper;
import com.tangerine.productmng.exception.BusinessException;
import com.tangerine.productmng.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginResource {

    private final AuthService authService;

    private final LoginServiceMapper mapper;

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) throws BusinessException {
        logger.info("going to login with username -> [{}]", request.username());
        var result = authService.login(mapper.toLoginModel(request));
        return ResponseEntity.ok(new LoginResponse(result.token()));
    }

}
