package id.ac.ui.cs.advprog.eshop.mcsauthentication.repository;

import id.ac.ui.cs.advprog.eshop.mcsauthentication.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    @Query(value = "SELECT r.* FROM USERS u " +
            "INNER JOIN USER_ROLE ur ON u.id = ur.user_id " +
            "INNER JOIN ROLES r ON ur.role_id = r.id " +
            "INNER JOIN ROLE_MENU rm ON r.id = rm.role_id " +
            "INNER JOIN MENUS m ON rm.menu_id = m.id " +
            "WHERE u.username = :username AND m.url = :menuUrl", nativeQuery = true)
    public List<Role> findRoleByUsernameAndMenuUrl(@Param("username") String username, @Param("menuUrl") String menuUrl);
}
