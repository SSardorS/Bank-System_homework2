package uz.pdp.homework2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Bank {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String bankName;

    @Column(nullable = false, unique = true)
    private String mfo;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private List<Card> card;

    @OneToMany(mappedBy = "ownerBankomat", cascade = CascadeType.ALL)
    private Set<BankomatResponsibleManager> bankomats;
}
