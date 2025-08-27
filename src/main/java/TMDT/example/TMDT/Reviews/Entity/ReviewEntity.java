package TMDT.example.TMDT.Reviews.Entity;

import TMDT.example.TMDT.Orders.Entity.OrderItemEntity;
import TMDT.example.TMDT.Products.Entity.ProductEntity;
import TMDT.example.TMDT.Products.Entity.ProductVariantEntity;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "review",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"buyer_id", "order_items_id"})
        }
)
public class ReviewEntity {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "buyer_id")
     private UserEntity buyer;

     @OneToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "order_items_id")
     private OrderItemEntity orderItem;
     private String comment;
     private int rating;
     private Date created_at;

}
