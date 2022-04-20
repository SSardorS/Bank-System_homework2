package uz.pdp.homework2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.homework2.entity.Bank;
import uz.pdp.homework2.entity.User;

import javax.persistence.ManyToOne;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankomatManagerDto {
    private Double minimumSumInBank;
    private Double maximumSumInBank;

    private Double sumNow;


    private UUID ownerBankomat;


    private UUID responsibleManager;
}
