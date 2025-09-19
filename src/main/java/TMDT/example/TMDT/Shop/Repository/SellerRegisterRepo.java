package TMDT.example.TMDT.Shop.Repository;

import TMDT.example.TMDT.Enums.RegisterStatus;
import TMDT.example.TMDT.Shop.Entity.SellerRegisterEntity;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRegisterRepo extends JpaRepository<SellerRegisterEntity,Long >{
    @Query("select s from SellerRegisterEntity s  join fetch s.user where s.id= :id ")
    SellerRegisterEntity findByIdCustom(Long id);
    boolean existsByUserId(Long idUsers);
    @Query(value = "select s from SellerRegisterEntity s  join fetch  s.user where s.status = :status")
    List<SellerRegisterEntity> findAllByStatus(RegisterStatus status);

}
