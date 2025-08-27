package TMDT.example.TMDT.Orders.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "return_request")
public class ReturnRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderItems_id")
    private OrderItemEntity order;


    private String reason;
    private String status;
    private LocalDateTime createdAt;
}
