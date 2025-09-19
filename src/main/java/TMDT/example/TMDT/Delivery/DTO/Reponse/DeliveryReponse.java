package TMDT.example.TMDT.Delivery.DTO.Reponse;

import TMDT.example.TMDT.Delivery.Entity.ShopDeliveryServiceEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeliveryReponse {
    private Long id;
    private String name;
    private String description;
    private String code;
    private String token;
    private String apiUrlCalculateFee;
    private String apiUrlCreateOrder;
    private String apiUrlTracking;
    private Long service_type_id;
    private String logoUrl;
    private boolean isActive;
}
