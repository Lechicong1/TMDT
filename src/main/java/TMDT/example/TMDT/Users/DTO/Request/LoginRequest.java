package TMDT.example.TMDT.Users.DTO.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String email;
    private String phone;
    private String username;
    private String password;
}
