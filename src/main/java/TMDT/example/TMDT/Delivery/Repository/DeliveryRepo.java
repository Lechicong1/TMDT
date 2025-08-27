package TMDT.example.TMDT.Delivery.Repository;

import TMDT.example.TMDT.Delivery.Entity.DeliveryServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepo extends JpaRepository<DeliveryServiceEntity,Long> {
}
