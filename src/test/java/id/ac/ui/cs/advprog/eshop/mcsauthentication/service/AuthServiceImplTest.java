package id.ac.ui.cs.advprog.eshop.mcsauthentication.service;

import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.CheckTokenRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.LoginRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.SignupRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.response.LoginResponse;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.response.MessageResponse;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.model.Role;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.model.User;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.model.UserDetailsImpl;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    PasswordEncoder encoder;

    @Mock
    UserService userService;

    @Mock
    RoleService roleService;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    AuthServiceImpl service;

    User user;

    String dummy = "dummy";

    SignupRequest signupRequest;

    CheckTokenRequest tokenRequest;

    String unauthorized = "Unauthorized access.";

    @BeforeEach
    void setUp(){
        user = new User(
                "user",
                "user@dummy",
                dummy,
                dummy,
                dummy,
                new Date()
        );

        Role role = new Role(dummy);
        user.setRoles(Set.of(role));

        signupRequest = new SignupRequest();
        signupRequest.setUsername(dummy);
        signupRequest.setEmail(dummy);
        signupRequest.setPassword(dummy);
        signupRequest.setFirstName(dummy);
        signupRequest.setLastName(dummy);
        signupRequest.setDob(new Date());

        tokenRequest = new CheckTokenRequest(dummy);
    }

    @Test
    void testLogin(){
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authenticationManager.authenticate(any())).thenReturn(authentication);
        Mockito.when(jwtUtils.generateJwtToken(any())).thenReturn(dummy);
        Mockito.when(authentication.getPrincipal()).thenReturn(UserDetailsImpl.build(user));

        LoginResponse response = (LoginResponse) service.login(new LoginRequest()).get("data");
        assertEquals(user.getUsername(), response.getUsername());

        Mockito.verify(jwtUtils, Mockito.times(1)).generateJwtToken(authentication);
    }

    @Test
    void testLoginFail(){
        Mockito.when(authenticationManager.authenticate(any())).thenThrow(NullPointerException.class);

        HttpStatus response = (HttpStatus) service.login(new LoginRequest()).get("status");
        assertEquals(HttpStatus.UNAUTHORIZED, response);
    }

    @Test
    void testSignUpUsernameTaken(){
        Mockito.when(userService.existsByUsername(signupRequest.getUsername())).thenReturn(true);

        Map<String, Object> response =  service.signup(signupRequest);
        HttpStatus status = (HttpStatus) response.get("status");
        MessageResponse data = (MessageResponse) response.get("data");
        assertEquals(HttpStatus.BAD_REQUEST, status);
        assertEquals("Error: Username is already taken!", data.getMessage());
    }

    @Test
    void testSignUpEmailTaken(){
        Mockito.when(userService.existsByUsername(signupRequest.getUsername())).thenReturn(false);
        Mockito.when(userService.existsByEmail(signupRequest.getEmail())).thenReturn(true);

        Map<String, Object> response =  service.signup(signupRequest);
        HttpStatus status = (HttpStatus) response.get("status");
        MessageResponse data = (MessageResponse) response.get("data");
        assertEquals(HttpStatus.BAD_REQUEST, status);
        assertEquals("Error: Email is already in use!", data.getMessage());
    }

    @Test
    void testSignUpInvalidRoles(){
        Mockito.when(userService.existsByUsername(signupRequest.getUsername())).thenReturn(false);
        Mockito.when(userService.existsByEmail(signupRequest.getEmail())).thenReturn(false);
        Mockito.when(roleService.getUserRolesByNames(any())).thenThrow(AuthorizationServiceException.class);

        Map<String, Object> response =  service.signup(signupRequest);
        HttpStatus status = (HttpStatus) response.get("status");
        assertEquals(HttpStatus.BAD_REQUEST, status);
    }

    @Test
    void testSignUpException(){
        Mockito.when(userService.existsByUsername(signupRequest.getUsername())).thenReturn(false);
        Mockito.when(userService.existsByEmail(signupRequest.getEmail())).thenReturn(false);
        Mockito.when(roleService.getUserRolesByNames(any())).thenReturn(user.getRoles());
        Mockito.when(userService.createRequestedUserWithRoles(any(), any())).thenThrow(NullPointerException.class);

        Map<String, Object> response =  service.signup(signupRequest);
        HttpStatus status = (HttpStatus) response.get("status");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, status);
    }

    @Test
    void testSignUp(){
        Mockito.when(userService.existsByUsername(signupRequest.getUsername())).thenReturn(false);
        Mockito.when(userService.existsByEmail(signupRequest.getEmail())).thenReturn(false);
        Mockito.when(roleService.getUserRolesByNames(any())).thenReturn(user.getRoles());
        Mockito.when(userService.createRequestedUserWithRoles(any(), any())).thenReturn(user);
        Mockito.when(encoder.encode(dummy)).thenReturn(dummy);

        Map<String, Object> response =  service.signup(signupRequest);
        HttpStatus status = (HttpStatus) response.get("status");
        assertEquals(HttpStatus.OK, status);
    }

    @Test
    void testCheckTokenNotValid(){
        Mockito.when(jwtUtils.validateJwtToken(tokenRequest.getToken())).thenReturn(false);

        assertEquals(unauthorized, service.checkToken(tokenRequest).getMessage());
    }

    @Test
    void testCheckTokenNoPermission(){
        Mockito.when(jwtUtils.validateJwtToken(tokenRequest.getToken())).thenReturn(true);
        Mockito.when(jwtUtils.getUserNameFromJwtToken(tokenRequest.getToken())).thenReturn(null);

        assertEquals(unauthorized, service.checkToken(tokenRequest).getMessage());
    }

    @Test
    void testCheckTokenInvalidUsername(){
        Mockito.when(jwtUtils.validateJwtToken(tokenRequest.getToken())).thenReturn(true);
        Mockito.when(jwtUtils.getUserNameFromJwtToken(tokenRequest.getToken())).thenReturn("");

        assertEquals(unauthorized, service.checkToken(tokenRequest).getMessage());
    }

    @Test
    void testCheckTokenException(){
        Mockito.when(jwtUtils.validateJwtToken(tokenRequest.getToken())).thenReturn(true);
        Mockito.when(jwtUtils.getUserNameFromJwtToken(tokenRequest.getToken())).thenReturn(user.getUsername());
        Mockito.when(userDetailsService.loadUserByUsername(user.getUsername())).thenThrow(UsernameNotFoundException.class);

        assertEquals(unauthorized, service.checkToken(tokenRequest).getMessage());
    }

    @Test
    void testCheckToken(){

        Mockito.when(jwtUtils.validateJwtToken(tokenRequest.getToken())).thenReturn(true);
        Mockito.when(jwtUtils.getUserNameFromJwtToken(tokenRequest.getToken())).thenReturn(user.getUsername());
        Mockito.when(userDetailsService.loadUserByUsername(user.getUsername())).thenReturn(UserDetailsImpl.build(user));

        assertEquals("Token validation success.", service.checkToken(tokenRequest).getMessage());
    }
}
