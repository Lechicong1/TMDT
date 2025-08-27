package TMDT.example.TMDT.Shop.Payload.Reponse;

import TMDT.example.TMDT.Users.Entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterSellerReponse {

    private Long id;
    private UserEntity user;
    private String shopName;
    private String identityDocumentUrl;
    private String status;
}
