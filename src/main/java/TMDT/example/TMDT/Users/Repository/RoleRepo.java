package TMDT.example.TMDT.Users.Repository;

import TMDT.example.TMDT.Enums.Role;
import TMDT.example.TMDT.Users.Entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<RolesEntity, Long> {
    RolesEntity findByRoleName(Role roleName);
}
