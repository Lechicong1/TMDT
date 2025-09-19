package TMDT.example.TMDT.Shop.Entity;

import TMDT.example.TMDT.Enums.ShopStatus;
import TMDT.example.TMDT.Products.Entity.ProductEntity;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shop")
public class ShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", unique = true)
    private UserEntity seller;
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductEntity> products;
    private String shopName;
    private String shopDescription;
    private String shopLogoUrl;
    private String shopAddress;

    @Enumerated(EnumType.STRING)
    private ShopStatus status; // ACTIVE | DISABLED | BANNED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
