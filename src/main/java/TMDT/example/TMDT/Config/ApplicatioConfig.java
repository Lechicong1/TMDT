package TMDT.example.TMDT.Config;

import TMDT.example.TMDT.Delivery.Entity.DeliveryServiceEntity;
import TMDT.example.TMDT.Delivery.Repository.DeliveryServiceRepo;
import TMDT.example.TMDT.Enums.CodeDeliveryService;
import TMDT.example.TMDT.Enums.Folder;
import TMDT.example.TMDT.Enums.Role;
import TMDT.example.TMDT.Users.Entity.RolesEntity;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import TMDT.example.TMDT.Users.Entity.UserRolesEntity;
import TMDT.example.TMDT.Users.Repository.RoleRepo;
import TMDT.example.TMDT.Users.Repository.UserRepo;
import TMDT.example.TMDT.Users.Repository.UserRoleRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class ApplicatioConfig {

   private final PasswordEncoder passwordEncoder;
   private final UserRoleRepo userRoleRepo;
   private final RoleRepo roleRepo;
   private final UserRepo userRepo;
   private final DeliveryServiceRepo  deliveryServiceRepo;
    @Value("${delivery.ghn.token}")
    private String ghnToken;
    @Bean
    ApplicationRunner runner() {
        return args -> {

            for (Role roleType : Role.values()) {
                if (roleRepo.findByRoleName(roleType) == null) {
                    RolesEntity role = new RolesEntity();
                    role.setRoleName(roleType);
                    roleRepo.save(role);
                }
            }
            if (userRepo.findByUsername("admin") == null) {
                RolesEntity adminRole = roleRepo.findByRoleName(Role.ADMIN);
                if (adminRole == null) {
                    throw new IllegalStateException("ADMIN role not found. Seed roles first.");
                }
                UserEntity user = new UserEntity();
                user.setUsername("admin");
                user.setEmail("admin");
                user.setPassword(passwordEncoder.encode("admin"));
                userRepo.save(user);
                UserRolesEntity userRole = new UserRolesEntity();
                userRole.setUser(user);
                userRole.setRole(adminRole);
                userRoleRepo.save(userRole);
            }
            // tao dvvc
            createDeliveryService();
        };
    }
    private void createDeliveryService(){
        if (deliveryServiceRepo.findByCode(CodeDeliveryService.GHN.name()).isEmpty()) {
            DeliveryServiceEntity ghn = new DeliveryServiceEntity();
            ghn.setName("Giao Hàng Nhanh");
            ghn.setCode(CodeDeliveryService.GHN.name());
            ghn.setApiUrlCalculateFee("https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee");
            ghn.setApiUrlCreateOrder("https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create");
            ghn.setApiUrlTracking("https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/detail");
            ghn.setToken(ghnToken);
            ghn.setService_type_id(2L);
            ghn.setActive(true);
            deliveryServiceRepo.save(ghn);
        }
    }

    }
