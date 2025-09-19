package TMDT.example.TMDT.Shop.Payload.Request;

import TMDT.example.TMDT.Enums.RegisterStatus;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class RegisterSellerRequest {
    private String shopName;


}
