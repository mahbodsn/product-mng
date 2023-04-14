package com.tangerine.productmng.service.auth.impl;

import com.tangerine.productmng.config.JwtTokenProvider;
import com.tangerine.productmng.service.auth.AuthService;
import com.tangerine.productmng.service.auth.model.LoginModel;
import com.tangerine.productmng.service.auth.model.LoginResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginResult login(LoginModel model) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(model.username(), model.password()));
        String token = jwtTokenProvider.createToken(authentication);
        return new LoginResult(token);
    }
}
