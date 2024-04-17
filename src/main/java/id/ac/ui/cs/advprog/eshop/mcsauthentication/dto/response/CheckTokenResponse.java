package id.ac.ui.cs.advprog.eshop.mcsauthentication.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@Setter
@Getter
public class CheckTokenResponse {
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
}
