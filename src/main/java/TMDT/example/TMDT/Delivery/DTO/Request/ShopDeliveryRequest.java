package TMDT.example.TMDT.Delivery.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
public class ShopDeliveryRequest {
    private List<Long > deliveryServiceIds;
    private boolean enabled;
    private BigDecimal baseShippingFee;
}
