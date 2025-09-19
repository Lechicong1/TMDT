package TMDT.example.TMDT.Users.Repository;

import TMDT.example.TMDT.Users.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    @Query(value = "select u.id from users u where u.username = :username",nativeQuery = true)
    Long findIdUserByUsername(String username);
    boolean existsByUsername(String username);
    @Query(value = "select u from  UserEntity u where u.email = :emailOrPhone or u.phone = :emailOrPhone")
    UserEntity findByEmailOrPhone(String emailOrPhone);
    @Query(value = "select u from UserEntity u join fetch  u.shop where u.id = :id ")
    UserEntity findByIdFetchShop(Long id);
}
