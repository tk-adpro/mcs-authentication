package id.ac.ui.cs.advprog.eshop.mcsauthentication.service;

import id.ac.ui.cs.advprog.eshop.mcsauthentication.enums.RoleName;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.model.Role;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Set<Role> getUserRolesByNames(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER.getValue())
                .orElseThrow(() -> new AuthorizationServiceException("Error: Role is not found."));
        roles.add(userRole);

        if (strRoles != null) {
            strRoles.forEach(role -> {
                Role currentRole = roleRepository.findByName(role)
                        .orElseThrow(() -> new AuthorizationServiceException("Error: Role is not found."));
                roles.add(currentRole);
            });
        }
        return roles;
    }

    @Override
    public boolean hasPermissionByMenuUrlAndUsername(String menuUrl, String username) {
        List<Role> roles = roleRepository.findRoleByUsernameAndMenuUrl(username, menuUrl);
        return !roles.isEmpty();
    }
}
