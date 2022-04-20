package uz.pdp.homework2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankomatResponsibleManager {
    @Id
    @GeneratedValue
    private UUID id;

    private Double minimumSumInBank;
    private Double maximumSumInBank;

    private Double sumNow;

    @ManyToOne
    private Bank ownerBankomat;

    @ManyToOne
    private User responsibleManager;

    @OneToMany(mappedBy = "bankomatResponsibleManager", cascade = CascadeType.ALL)
    private Set<Bankomat> bankomats;
}
