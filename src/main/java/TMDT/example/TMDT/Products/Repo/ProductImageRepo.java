package TMDT.example.TMDT.Products.Repo;

import TMDT.example.TMDT.Products.Entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepo extends JpaRepository<ProductImageEntity,Integer> {
}
