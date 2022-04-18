package uz.pdp.homework2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.homework2.payload.ApiResponse;
import uz.pdp.homework2.payload.CardDto;
import uz.pdp.homework2.service.CardService;

import java.text.ParseException;

@RestController
@RequestMapping("/api/card")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CardController {

    @Autowired
    CardService cardService;

//    @PreAuthorize(value = "hasAnyRole('DIRECTOR','ACCAUNTANT','MANAGER')")
    @Secured("DIRECTOR")
    @PostMapping
    public HttpEntity<?> add(@RequestBody CardDto cardDto) throws ParseException {
        ApiResponse add = cardService.add(cardDto);
        return ResponseEntity.status(add.isSucces()?200:409).body(add);
    }
}
