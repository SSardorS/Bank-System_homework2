package uz.pdp.homework2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.homework2.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponceObjects {
    private ApiResponse apiResponse;
    private User user;

}
