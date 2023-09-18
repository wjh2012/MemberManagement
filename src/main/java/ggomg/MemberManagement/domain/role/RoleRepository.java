package ggomg.MemberManagement.domain.role;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);
}
