package TMDT.example.TMDT.Shop.Repository;

import TMDT.example.TMDT.Shop.Entity.ShopEntity;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepo extends JpaRepository<ShopEntity, Long> {
    ShopEntity findBySeller(UserEntity user);
}
