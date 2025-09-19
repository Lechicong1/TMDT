package TMDT.example.TMDT.Cart.Repo;

import TMDT.example.TMDT.Cart.Entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<CartEntity,Long> {
}
