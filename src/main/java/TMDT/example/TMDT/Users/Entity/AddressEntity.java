package TMDT.example.TMDT.Users.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class AddressEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private UserEntity user;
        private String recipientName;
        private String recipientPhone;
        private String province;
        private String district;
        private String ward;
        private String streetAddress;
        private boolean addressDefault;
        private LocalDateTime createdAt;

}
