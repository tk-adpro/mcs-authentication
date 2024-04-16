package id.ac.ui.cs.advprog.eshop.mcsauthentication.service;

import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.CheckTokenRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.LoginRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.SignupRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.response.CheckTokenResponse;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.response.LoginResponse;

import java.util.Map;

public interface AuthService {
    Map<String, Object> login(LoginRequest loginRequest);
    Map<String, Object> signup(SignupRequest signUpRequest);
    CheckTokenResponse checkToken(CheckTokenRequest request);
}
