package id.ac.ui.cs.advprog.eshop.mcsauthentication.controller;

import java.util.*;
import java.util.stream.Collectors;

import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.CheckTokenRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.LoginRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.SignupRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.response.MessageResponse;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.service.AuthService;
import jakarta.validation.Valid;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Log4j2
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Map<String, Object> responseMap = authService.login(loginRequest);
        Object data = responseMap.get("data");
        HttpStatus status = (HttpStatus) responseMap.get("status");
        return new ResponseEntity<>(data, status);
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> signup(@Valid @RequestBody SignupRequest signUpRequest){
        Map<String, Object> responseMap = authService.signup(signUpRequest);
        MessageResponse message = (MessageResponse) responseMap.get("data");
        HttpStatus status = (HttpStatus) responseMap.get("status");
        return new ResponseEntity<>(message, status);
    }


    @PostMapping("/check-token")
    public ResponseEntity<?> checkToken(@Valid @RequestBody CheckTokenRequest request){
        return ResponseEntity.ok(authService.checkToken(request));
    }
}
