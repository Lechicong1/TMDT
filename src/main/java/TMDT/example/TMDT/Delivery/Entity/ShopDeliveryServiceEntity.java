package TMDT.example.TMDT.Delivery.Entity;

import TMDT.example.TMDT.Shop.Entity.ShopEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shop_delivery_service")
public class ShopDeliveryServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private ShopEntity shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_service_id")
    private DeliveryServiceEntity deliveryService;


    private boolean isEnabled;
    private String externalServiceCode;
    private String token;
    private BigDecimal baseShippingFee;
}
