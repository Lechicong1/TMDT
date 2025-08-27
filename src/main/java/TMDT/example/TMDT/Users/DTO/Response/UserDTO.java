package TMDT.example.TMDT.Users.DTO.Response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private boolean verified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
