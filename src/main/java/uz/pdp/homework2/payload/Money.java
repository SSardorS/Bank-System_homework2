package uz.pdp.homework2.payload;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import uz.pdp.homework2.entity.enums.Moneys;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
public class Money {
    private Double sum;
    @Enumerated(value = EnumType.STRING)
    private Moneys moneyType;
}
