package TMDT.example.TMDT.Users.DTO.Response;

import TMDT.example.TMDT.Users.Entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class AddressDTO {
    private Long id;
    private String recipientName;
    private String recipientPhone;
    private String province;
    private String district;
    private String ward;
    private String streetAddress;
    private boolean addressDefault;
    private LocalDateTime createdAt;
}
