package uz.pdp.homework2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.homework2.entity.enums.CardType;
import uz.pdp.homework2.entity.enums.Moneys;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Bankomat {
    @Id
    @GeneratedValue
    private UUID id;


    private UUID withDrawCardId;

    private Double withdrawSum;

    @ManyToOne
    private BankomatResponsibleManager bankomatResponsibleManager;










}
