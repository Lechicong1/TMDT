package TMDT.example.TMDT.Shop.Entity;

import TMDT.example.TMDT.Enums.RegisterStatus;
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
@Table(name = "seller_register")
public class SellerRegisterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;

    private String shopName;
    private String identityDocumentUrl;

    @Enumerated(EnumType.STRING)
    private RegisterStatus status; // PENDING | APPROVED | REJECTED

    private LocalDateTime submittedAt;
    private LocalDateTime reviewedAt;
}
