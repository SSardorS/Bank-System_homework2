package uz.pdp.homework2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.homework2.entity.Bank;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardDto {
    private String cardNumber;
    private UUID bankId;
    private String ccv;
    private Date expiredDate;
    private String password;
    private UUID userId;

}
