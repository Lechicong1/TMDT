package TMDT.example.TMDT.Users.Repository;

import TMDT.example.TMDT.Enums.Role;
import TMDT.example.TMDT.Users.Entity.UserEntity;
import TMDT.example.TMDT.Users.Entity.UserRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRolesEntity,Long> {
    @Query(value = "select ur from UserRolesEntity ur join fetch ur.role where ur.user =:user")
    List<UserRolesEntity> findAllByUser(UserEntity user);
}
