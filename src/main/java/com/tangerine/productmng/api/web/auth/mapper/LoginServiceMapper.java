package com.tangerine.productmng.api.web.auth.mapper;

import com.tangerine.productmng.api.web.auth.dto.LoginRequest;
import com.tangerine.productmng.service.auth.model.LoginModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginServiceMapper {

    LoginModel toLoginModel(LoginRequest request);

}
