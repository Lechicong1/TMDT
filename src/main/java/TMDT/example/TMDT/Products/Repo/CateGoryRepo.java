package TMDT.example.TMDT.Products.Repo;

import TMDT.example.TMDT.Products.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CateGoryRepo extends JpaRepository<CategoryEntity, Long> {
}
