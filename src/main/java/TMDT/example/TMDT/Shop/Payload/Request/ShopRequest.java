package TMDT.example.TMDT.Shop.Payload.Request;

import TMDT.example.TMDT.Enums.ShopStatus;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ShopRequest {
    private String shopName;
    private String shopDescription;
    private String shopAddress;
    private ShopStatus status; // ACTIVE | DISABLED | BANNED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
