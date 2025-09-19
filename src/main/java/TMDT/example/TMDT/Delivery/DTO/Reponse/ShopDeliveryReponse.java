package TMDT.example.TMDT.Delivery.DTO.Reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShopDeliveryReponse {
        private Long id ;
        private String name ;
        private boolean enabled;
        private String logo;

}
