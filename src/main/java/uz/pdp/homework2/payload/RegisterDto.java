package uz.pdp.homework2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDto {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Integer roleId;
}
