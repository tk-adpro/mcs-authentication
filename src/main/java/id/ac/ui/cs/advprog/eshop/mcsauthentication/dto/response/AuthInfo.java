package id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthInfo {
    private Long id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private List<String> authorities;
}
