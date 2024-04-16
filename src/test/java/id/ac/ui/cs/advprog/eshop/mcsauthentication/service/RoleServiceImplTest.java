package id.ac.ui.cs.advprog.eshop.mcsauthentication.service;

import id.ac.ui.cs.advprog.eshop.mcsauthentication.enums.RoleName;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.model.Role;
import id.ac.ui.cs.advprog.eshop.mcsauthentication.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RoleServiceImpl service;

    Set<Role> roles;

    Set<String> strRoles;

    String dummy = "dummy";

    @BeforeEach
    void setUp(){
        roles = new HashSet<>();
        roles.add(new Role(dummy));

        strRoles = new HashSet<>();
        strRoles.add(dummy);
    }

    @Test
    void testGetUserRoleNotFoundRoleUser(){
        Mockito.when(roleRepository.findByName(RoleName.ROLE_USER.getValue())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.getUserRolesByNames(strRoles);
        });

        assertTrue(exception.getMessage().contains("Error: Role is not found."));
    }

    @Test
    void testGetUserRoleNullStrRoles(){
        Mockito.when(roleRepository.findByName(RoleName.ROLE_USER.getValue())).thenReturn(Optional.of(new Role(RoleName.ROLE_USER.getValue())));

        Set<Role> result = service.getUserRolesByNames(null);
        assertEquals(1, result.size());
        assertEquals(RoleName.ROLE_USER.getValue(), result.iterator().next().getName());
    }

    @Test
    void testGetUserRoleNotFoundRole(){

        Mockito.when(roleRepository.findByName(RoleName.ROLE_USER.getValue())).thenReturn(Optional.of(new Role()));
        Mockito.when(roleRepository.findByName(dummy)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.getUserRolesByNames(strRoles);
        });

        assertTrue(exception.getMessage().contains("Error: Role is not found."));
        Mockito.verify(roleRepository, Mockito.times(1)).findByName(RoleName.ROLE_USER.getValue());
        Mockito.verify(roleRepository, Mockito.times(1)).findByName(dummy);

    }

    @Test
    void testGetUserRoleFoundRole(){

        Mockito.when(roleRepository.findByName(RoleName.ROLE_USER.getValue())).thenReturn(Optional.of(new Role(RoleName.ROLE_USER.getValue())));
        Mockito.when(roleRepository.findByName(dummy)).thenReturn(Optional.of(new Role(dummy)));

        Set<Role> result = service.getUserRolesByNames(strRoles);
        assertEquals(2, result.size());
        Mockito.verify(roleRepository, Mockito.times(1)).findByName(RoleName.ROLE_USER.getValue());
        Mockito.verify(roleRepository, Mockito.times(1)).findByName(dummy);

        Iterator<Role> iterator = result.iterator();
        assertEquals(iterator.next().getName(), RoleName.ROLE_USER.getValue());
        assertEquals(iterator.next().getName(), dummy);

    }

    @Test
    void testNoPermissionByMenuUrlAndUsername(){
        Mockito.when(roleRepository.findRoleByUsernameAndMenuUrl("user", "menu")).thenReturn(new ArrayList<>());

        assertFalse(service.hasPermissionByMenuUrlAndUsername("menu", "user"));
    }

    @Test
    void testHasPermissionByMenuUrlAndUsername(){
        Mockito.when(roleRepository.findRoleByUsernameAndMenuUrl("user", "menu")).thenReturn(List.of(new Role()));

        assertTrue(service.hasPermissionByMenuUrlAndUsername("menu", "user"));
    }
}
