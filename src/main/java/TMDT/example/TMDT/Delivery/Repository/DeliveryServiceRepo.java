package TMDT.example.TMDT.Delivery.Repository;

import TMDT.example.TMDT.Delivery.Entity.DeliveryServiceEntity;
import TMDT.example.TMDT.Enums.CodeDeliveryService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryServiceRepo extends JpaRepository<DeliveryServiceEntity,Long> {
    List<DeliveryServiceEntity> findAllByIdIn(List<Long> deliveryServiceIds);
    List<DeliveryServiceEntity> findByCode(String code);
}
