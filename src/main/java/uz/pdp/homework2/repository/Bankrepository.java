package uz.pdp.homework2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.homework2.entity.Bank;

import java.util.UUID;

@RepositoryRestResource(path = "bank")
public interface Bankrepository extends JpaRepository<Bank, UUID> {
}
