package TMDT.example.TMDT.Delivery.DTO.Request;

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
public class CreateDeliveryCarrierRequest {
    private String name;
    private String description;

    private boolean active;

}
