package uz.pdp.homework2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.homework2.entity.BankomatResponsibleManager;

import java.util.Set;
import java.util.UUID;

public interface BankomatResposibleManagaerRepository extends JpaRepository<BankomatResponsibleManager, UUID> {


    @Query(value = "select b from bankomat_reponsible_manager b where minimum_sum_in_bank>:sumNow",nativeQuery = true)
    Set<BankomatResponsibleManager> findBySumNow(Double sumNow);


}
