package id.ac.ui.cs.advprog.eshop.mcsauthentication.controller;

import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.CheckTokenRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.LoginRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.SignupRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.response.CheckTokenResponse;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.response.MessageResponse;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Log4j2
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest) {
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
    public ResponseEntity<CheckTokenResponse> checkToken(@Valid @RequestBody CheckTokenRequest request){
        return ResponseEntity.ok(authService.checkToken(request));
    }
}
