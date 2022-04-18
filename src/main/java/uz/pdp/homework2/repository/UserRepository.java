package uz.pdp.homework2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.homework2.entity.User;

import javax.validation.constraints.Email;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findAllByEmail(@Email String email);

    boolean existsByEmail(@Email String email);

    Optional<User> findByEmailAndEmailCode(@Email String email, String emailCode);
}
