package TMDT.example.TMDT.Users.Repository;

import TMDT.example.TMDT.Users.Entity.AddressEntity;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<AddressEntity, Long> {
    @Query("UPDATE AddressEntity a SET a.addressDefault = false WHERE a.user.id = :userId")
    void clearDefaultForUser(Long userId);

    List<AddressEntity> findAllByUser(UserEntity user);
}
