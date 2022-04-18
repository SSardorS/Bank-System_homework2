package uz.pdp.homework2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.homework2.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
