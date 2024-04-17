package id.ac.ui.cs.advprog.eshop.mcsauthentication.service;

import id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request.SignupRequest;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.model.Role;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.model.User;

import java.util.Set;

public interface UserService {
    public User createRequestedUserWithRoles(SignupRequest signupRequest, Set<Role> roles);

    public boolean existsByUsername(String username);

    public boolean existsByEmail(String email);

    public User findByUsername(String username);
}
