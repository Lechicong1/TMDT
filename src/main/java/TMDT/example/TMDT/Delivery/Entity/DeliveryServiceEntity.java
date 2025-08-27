package TMDT.example.TMDT.Delivery.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "delivery_service")
public class DeliveryServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    private String description;
    private String logoUrl;
    private boolean isActive;
    @OneToMany(mappedBy = "deliveryService", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ShopDeliveryServiceEntity> shopDeliveryServices;
}
