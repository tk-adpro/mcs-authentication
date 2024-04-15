package id.ac.ui.cs.advprog.eshop.mcsauthentication.model;

import id.ac.ui.cs.advprog.eshop.mcsauthentication.enums.RoleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {

    private List<Menu> menus;

    @BeforeEach
    void setUp(){

        Menu menu = new Menu();
        menu.setId(3);
        menu.setUrl("/test/all");

        this.menus = new ArrayList<>();
        this.menus.add(menu);

    }

    @Test
    void testSetterGetter(){
        Menu menu = this.menus.getFirst();
        Menu newMenu = new Menu();
        newMenu.setId(menu.getId());
        newMenu.setUrl(menu.getUrl());

        assertEquals(menu.getId(), newMenu.getId());
        assertEquals(menu.getUrl(), newMenu.getUrl());
    }
}
