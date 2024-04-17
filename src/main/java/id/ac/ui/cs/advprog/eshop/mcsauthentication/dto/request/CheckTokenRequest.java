package id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckTokenRequest {
    @NotBlank
    private String token;
    @NotBlank
    private String url;
}
