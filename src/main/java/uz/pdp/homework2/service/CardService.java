package uz.pdp.homework2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.security.util.Password;
import uz.pdp.homework2.entity.Bank;
import uz.pdp.homework2.entity.Card;
import uz.pdp.homework2.entity.User;
import uz.pdp.homework2.payload.ApiResponse;
import uz.pdp.homework2.payload.CardDto;
import uz.pdp.homework2.repository.Bankrepository;
import uz.pdp.homework2.repository.CardRepository;
import uz.pdp.homework2.repository.UserRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    Bankrepository bankrepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponse add(CardDto cardDto) {
        boolean cardNumber = cardRepository.existsByCardNumber(cardDto.getCardNumber());


        Date date = new Date();
        if (cardNumber && date.after(cardDto.getExpiredDate())){
            Bank bank = bankrepository.getOne(cardDto.getBankId());
            User user = userRepository.getOne(cardDto.getUserId());
            Card card = new Card(cardDto.getCardNumber(),bank, cardDto.getCcv(), cardDto.getExpiredDate(), passwordEncoder.encode(cardDto.getPassword()),user);
            cardRepository.save(card);
            return new ApiResponse("Added card", true);
        }
        return new ApiResponse("Something is wrong", false);
    }
}
