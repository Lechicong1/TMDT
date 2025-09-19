package TMDT.example.TMDT.Cart.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {
    Long variantId;
    Integer quantityItem;
}
