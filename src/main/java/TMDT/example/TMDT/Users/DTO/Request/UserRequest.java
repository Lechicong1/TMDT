package TMDT.example.TMDT.Users.DTO.Request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private String phone;
    private boolean verified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
