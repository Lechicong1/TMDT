package TMDT.example.TMDT.Users.Entity;


import TMDT.example.TMDT.Cart.Entity.CartEntity;
import TMDT.example.TMDT.Chat.Entity.ChatMessageEntity;
import TMDT.example.TMDT.Notification.Entity.NotificationEntity;
import TMDT.example.TMDT.Orders.Entity.OrderEntity;
import TMDT.example.TMDT.Revenue.Entity.RevenueEntity;
import TMDT.example.TMDT.Reviews.Entity.ReviewEntity;
import TMDT.example.TMDT.Shop.Entity.ShopEntity;
import TMDT.example.TMDT.Vouchers.Entity.VoucherEntity;
import TMDT.example.TMDT.Wishlist.Entity.WhistListEntity;
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
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = true)
    private String email;

    private String phone;


    private boolean verified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AddressEntity> addresses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserRolesEntity> userRoles;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VoucherEntity> vouchers;


    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WhistListEntity> wishlist;


    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RevenueEntity> revenues;


    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartEntity> cartItems;


    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderEntity> orders;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NotificationEntity> notifications;


    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatMessageEntity> sentMessages;


    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatMessageEntity> receivedMessages;

    @OneToOne(mappedBy = "seller",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private ShopEntity shop;

    @OneToMany(mappedBy = "buyer",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ReviewEntity> reviews;

}
