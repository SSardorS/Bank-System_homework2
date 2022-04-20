package uz.pdp.homework2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.homework2.entity.Card;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
    boolean existsByCardNumber(String cardNumber);

    Optional<Card> findByCardNumberAndPassword(String cardNumber, String password);
}
