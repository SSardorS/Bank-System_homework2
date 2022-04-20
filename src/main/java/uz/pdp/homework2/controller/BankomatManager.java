package uz.pdp.homework2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.homework2.payload.ApiResponse;
import uz.pdp.homework2.payload.BankomatManagerDto;
import uz.pdp.homework2.service.BankomatResponsibleManagerService;

@RestController
@RequestMapping("/api/bankomatManager")
public class BankomatManager {

    @Autowired
    BankomatResponsibleManagerService bankomatResponsibleManagerService;

    @PostMapping
    public HttpEntity<?> add(@RequestBody BankomatManagerDto bankomatManagerDto){
        ApiResponse apiResponse = bankomatResponsibleManagerService.addBanomatSettings(bankomatManagerDto);
        return ResponseEntity.status(apiResponse.isSucces()?200:409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> get(){
        ApiResponse info = bankomatResponsibleManagerService.getInfo();
        return ResponseEntity.status(info.isSucces()?200:409).body(info);
    }
}
