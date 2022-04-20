package uz.pdp.homework2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankomatDto {
    private String cardNumber;
    private String password;
    private Double withdrawSum;

    private UUID bankomatResponsibleManagerId;

}
