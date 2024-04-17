package id.ac.ui.cs.advprog.eshop.mcsauthentication.enums;

import lombok.Getter;

@Getter
public enum RoleName {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_CUSTOMER("ROLE_CUSTOMER");

    private final String value;

    private RoleName(String value){
        this.value = value;
    }

}
