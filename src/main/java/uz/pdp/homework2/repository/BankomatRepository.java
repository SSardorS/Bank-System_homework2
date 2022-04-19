package uz.pdp.homework2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.homework2.entity.Bankomat;

import java.util.UUID;

public interface BankomatRepository extends JpaRepository<Bankomat, UUID> {

}
