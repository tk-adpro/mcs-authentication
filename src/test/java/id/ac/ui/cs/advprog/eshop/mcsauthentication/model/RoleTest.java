package id.ac.ui.cs.advprog.eshop.mcsauthentication.model;

import id.ac.ui.cs.advprog.eshop.mcsauthentication.enums.RoleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    private List<Role> roles;

    private Set<Menu> menus;

    @BeforeEach
    void setUp(){
        this.roles = new ArrayList<>();

        Role role1 = new Role(RoleName.ROLE_USER.getValue());
        Role role2 = new Role(RoleName.ROLE_ADMIN.getValue());

        this.roles.add(role1);
        this.roles.add(role2);

        this.menus = new HashSet<>();
        this.menus.add(new Menu());
        this.menus.add(new Menu());

    }

    @Test
    void testGetterSetter(){
        Role role = roles.getFirst();
        Role newRole = new Role();
        newRole.setId(role.getId());
        newRole.setName(role.getName());
        newRole.setMenus(menus);

        assertEquals(role.getId(), newRole.getId());
        assertEquals(role.getName(), newRole.getName());

        assertNotEquals(role.getMenus(), newRole.getMenus());
        assertEquals(menus, newRole.getMenus());
    }
}
