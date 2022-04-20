package uz.pdp.homework2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.homework2.entity.Bank;
import uz.pdp.homework2.entity.Bankomat;
import uz.pdp.homework2.entity.BankomatResponsibleManager;
import uz.pdp.homework2.entity.Card;
import uz.pdp.homework2.entity.enums.Moneys;
import uz.pdp.homework2.payload.ApiResponse;
import uz.pdp.homework2.payload.BankomatDto;
import uz.pdp.homework2.payload.Money;
import uz.pdp.homework2.repository.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BankomatService {

    @Autowired
    BankomatRepository bankomatRepository;

    @Autowired
    Bankrepository bankrepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    BankomatResposibleManagaerRepository bankomatResposibleManagaerRepository;

    List<Money> arrays = new ArrayList<>(
            Arrays.asList(
                    new Money(1000.00, Moneys.SUM),
                    new Money(5000.00, Moneys.SUM),
                    new Money(10000.00, Moneys.SUM),
                    new Money(50000.00, Moneys.SUM),
                    new Money(100000.00, Moneys.SUM),
                    new Money(1.00, Moneys.DOLLAR),
                    new Money(5.00, Moneys.DOLLAR),
                    new Money(10.00, Moneys.DOLLAR),
                    new Money(20.00, Moneys.DOLLAR),
                    new Money(50.00, Moneys.DOLLAR),
                    new Money(100.00, Moneys.DOLLAR)
            )
    );

    public ApiResponse withdtawMoaney(@RequestBody BankomatDto bankomatDto) {
        Optional<Card> byCardNumberAndPassword = cardRepository.findByCardNumberAndPassword(bankomatDto.getCardNumber(), bankomatDto.getPassword());
        if (!byCardNumberAndPassword.isPresent()) {
            return new ApiResponse("Password yoki plastik nomeri xato", false);
        }
        Card card = byCardNumberAndPassword.get();
        if (card.getSum() < bankomatDto.getWithdrawSum() * 1.1) {
            return new ApiResponse("Berilgan sumani yechib bolmaydi kartada ", false);
        }

        Optional<BankomatResponsibleManager> bankManager = bankomatResposibleManagaerRepository.findById(bankomatDto.getBankomatResponsibleManagerId());
        if (!bankManager.isPresent()) {
            return new ApiResponse("Bankomatdan foydalanishda extoyot boling nomalum mankomat", false);
        }
        BankomatResponsibleManager bankomatResponsibleManager = bankManager.get();
        if (bankomatDto.getWithdrawSum()>bankomatResponsibleManager.getSumNow() && bankomatDto.getWithdrawSum()>bankomatResponsibleManager.getMaximumSumInBank()){
            return new ApiResponse("Bankomatda pul yetarli emas", false);
        }


        Optional<Bank> byIdBankId = bankrepository.findById(card.getBank().getId());

        if (!byIdBankId.isPresent()) {
            return new ApiResponse("Malumotlar olishda  xatolika uchradi", false);
        }
        Bank bank = byIdBankId.get();

        Bankomat bankomat = new Bankomat();
        bankomat.setWithDrawCardId(card.getId());
        if (bank.getId().equals(card.getBank().getId())){
            bankomat.setWithdrawSum(bankomatDto.getWithdrawSum());
            double bankomatSum = bankomatResponsibleManager.getSumNow() - bankomat.getWithdrawSum();
            bankomatResponsibleManager.setSumNow(bankomatSum);
        }else {
            bankomat.setWithdrawSum(bankomatDto.getWithdrawSum()*1.1);
            double bankomatSum = bankomatResponsibleManager.getSumNow() - bankomatDto.getWithdrawSum() * 1.1;
            bankomatResponsibleManager.setSumNow(bankomatSum);
        }

        bankomatRepository.save(bankomat);
        return new ApiResponse("succesFulWithdraw", true);

    }


}
