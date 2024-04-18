package id.ac.ui.cs.advprog.eshop.mcsauthentication.service;

import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.CheckTokenRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.LoginRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.SignupRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.response.CheckTokenResponse;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.response.LoginResponse;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.response.MessageResponse;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.model.Role;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.model.User;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.model.UserDetailsImpl;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.utils.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public Map<String, Object> login(LoginRequest loginRequest) {
        Map<String, Object> responseMap = new HashMap<>();

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            LoginResponse response = new LoginResponse();
            response.setToken(jwt);
            response.setId(userDetails.getId());
            response.setUsername(userDetails.getUsername());
            response.setEmail(userDetails.getEmail());
            response.setRoles(roles);

            responseMap.put("status", HttpStatus.OK);
            responseMap.put("data", response);
        } catch (Exception e){
            MessageResponse response = new MessageResponse("Login failed. Please try again.");
            responseMap.put("status", HttpStatus.UNAUTHORIZED);
            responseMap.put("data", response);
        }

        return responseMap;
    }

    @Override
    public Map<String, Object> signup(SignupRequest signUpRequest) {
        Map<String, Object> response = new HashMap<>();
        MessageResponse message = new MessageResponse();
        HttpStatus status = HttpStatus.OK;

        try{
            validateSignupRequest(signUpRequest);

            Set<Role> roles = roleService.getUserRolesByNames(signUpRequest.getRole());

            signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
            User user = userService.createRequestedUserWithRoles(signUpRequest, roles);

            log.info("User registered successfully: {}", user.getUsername());

            message.setMessage("User registered successfully!");

        } catch (BadRequestException | AuthorizationServiceException e) {
            message.setMessage(e.getMessage());
            status = HttpStatus.BAD_REQUEST;

        } catch (Exception e){
            log.error(e.getMessage());
            message.setMessage("Internal server error");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        response.put("data", message);
        response.put("status", status);

        return response;
    }

    private void validateSignupRequest(SignupRequest request) throws BadRequestException {
        String message;
        if (userService.existsByUsername(request.getUsername())) {
            message = "Error: Username is already taken!";
            log.error(message);
            throw new BadRequestException(message);

        } else if (userService.existsByEmail(request.getEmail())) {
            message = "Error: Email is already in use!";
            log.error(message);
            throw new BadRequestException(message);

        }
    }

    @Override
    public CheckTokenResponse checkToken(CheckTokenRequest request) {
        CheckTokenResponse response = new CheckTokenResponse();
        response.setMessage("Unauthorized access.");

        String token = request.getToken();
        String menuUrl = request.getUrl();

        try {
            if (jwtUtils.validateJwtToken(token)) {
                String username = jwtUtils.getUserNameFromJwtToken(token);

                if (username != null && !username.isBlank()){
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    response.setData(authentication);
                    response.setMessage("Token validation success.");
                    log.info("Token validation success.");
                }
            }
        } catch (Exception e) {
            log.error("Failed to validate token. {}", e.getMessage());
        }

        return response;

    }
}
