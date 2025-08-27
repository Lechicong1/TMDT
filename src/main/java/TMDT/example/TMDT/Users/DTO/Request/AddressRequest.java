package TMDT.example.TMDT.Users.DTO.Request;

import TMDT.example.TMDT.Users.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class AddressRequest {
    private String recipientName;
    private String recipientPhone;
    private String province;
    private String district;
    private String ward;
    private String streetAddress;
    private boolean addressDefault;
    private LocalDateTime createdAt;

}
