package com.tangerine.productmng.service.auth;

import com.tangerine.productmng.service.auth.model.LoginModel;
import com.tangerine.productmng.service.auth.model.LoginResult;

public interface AuthService {

    LoginResult login(LoginModel model);

}
