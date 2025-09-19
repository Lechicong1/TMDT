package TMDT.example.TMDT.Delivery.Repository;

import TMDT.example.TMDT.Delivery.Entity.DeliveryServiceEntity;
import TMDT.example.TMDT.Delivery.Entity.ShopDeliveryServiceEntity;
import TMDT.example.TMDT.Shop.Entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ShopDeliveryRepo extends JpaRepository<ShopDeliveryServiceEntity,Long> {
    @Modifying
    @Query(value = "delete from shop_delivery_service s where s.delivery_service_id  in (:deliveryServiceIds) and s.shop_id=:shopId ",nativeQuery = true)
    void deleteByShopAndDeliveryService(Long shopId, List<Long> deliveryServiceIds);
    @Query("select s.deliveryService from ShopDeliveryServiceEntity s where s.shop =:shop")
    List<DeliveryServiceEntity> findDeliveryServiceByShop(ShopEntity shop);
    @Query(value = "select s.delivery_service_id from shop_delivery_service s where s.shop_id = :shopId ",nativeQuery = true)
    Set<Long> findIdDeliveryServiceByShopId(Long shopId);
    void deleteAllByShop(ShopEntity shopEntity);
}
