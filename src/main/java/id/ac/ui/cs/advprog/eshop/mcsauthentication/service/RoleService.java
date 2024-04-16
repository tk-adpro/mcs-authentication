package id.ac.ui.cs.advprog.eshop.mcsauthentication.service;

import id.ac.ui.cs.advprog.eshop.mcsauthentication.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    public Set<Role> getUserRolesByNames(Set<String> strRoles);

    public boolean hasPermissionByMenuUrlAndUsername(String menuUrl, String username);
}
