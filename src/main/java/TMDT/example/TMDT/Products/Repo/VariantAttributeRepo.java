package TMDT.example.TMDT.Products.Repo;

import TMDT.example.TMDT.Products.Entity.VariantAtrributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantAttributeRepo extends JpaRepository<VariantAtrributeEntity,Long> {
}
