package TMDT.example.TMDT.Products.Repo;

import TMDT.example.TMDT.Products.Entity.ProductVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductVariantRepo extends JpaRepository<ProductVariantEntity, Long> {
}
