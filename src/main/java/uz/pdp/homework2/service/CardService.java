package uz.pdp.homework2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.homework2.entity.Bank;
import uz.pdp.homework2.entity.Card;
import uz.pdp.homework2.entity.User;
import uz.pdp.homework2.entity.enums.CardType;
import uz.pdp.homework2.payload.ApiResponse;
import uz.pdp.homework2.payload.CardDto;
import uz.pdp.homework2.repository.Bankrepository;
import uz.pdp.homework2.repository.CardRepository;
import uz.pdp.homework2.repository.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


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

    public ApiResponse add(CardDto cardDto) throws ParseException {
        boolean cardNumber = cardRepository.existsByCardNumber(cardDto.getCardNumber());

        CardType cardType = cardType(cardDto.getCardNumber());

        if (cardType==null){
            return new ApiResponse("Carta turi aniqlanmadi", false);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date parse = simpleDateFormat.parse(cardDto.getExpiredDate());


        Date date = new Date();
        if (!cardNumber && date.before(parse)) {
            Bank bank = bankrepository.getOne(cardDto.getBankId());
            User user = userRepository.getOne(cardDto.getUserId());
            Card card = new Card(cardDto.getCardNumber(), bank, cardDto.getCcv(), parse, cardDto.getPassword(), user,cardType.name());
            cardRepository.save(card);
            return new ApiResponse("Added card", true);
        }
        return new ApiResponse("Something is wrong", false);
    }

    private CardType cardType(String cardNumber) {
        String cardCode = cardNumber.substring(0, 4);
        if (cardCode.equals("8600")){
            return CardType.UZCARD;
        }else if (cardCode.equals("9860")){
            return CardType.HUMO;
        }else if (cardCode.equals("4000")){
            return CardType.VISA;
        }else {
            return null;
        }
    }
}
