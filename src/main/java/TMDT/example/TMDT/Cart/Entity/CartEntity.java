package TMDT.example.TMDT.Cart.Entity;

import TMDT.example.TMDT.Products.Entity.ProductVariantEntity;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "cart",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"buyer_id", "variant_id"})
        }
)
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private UserEntity buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private ProductVariantEntity variant;

    private int quantity;
    private LocalDateTime addedAt;
}
