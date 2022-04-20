package uz.pdp.homework2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.homework2.payload.ApiResponse;
import uz.pdp.homework2.payload.BankomatDto;
import uz.pdp.homework2.service.BankomatService;

@RestController
@RequestMapping("/api/bankomat")
public class BankomatController {
    @Autowired
    BankomatService bankomatService;

    @PostMapping
    public HttpEntity<?> withdrawMoaney(@RequestBody BankomatDto bankomatDto) {
        ApiResponse apiResponse = bankomatService.withdtawMoaney(bankomatDto);
        return ResponseEntity.status(apiResponse.isSucces()?200:409).body(apiResponse);
    }

}
