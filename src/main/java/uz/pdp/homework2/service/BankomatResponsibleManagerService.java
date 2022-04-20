package uz.pdp.homework2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import uz.pdp.homework2.entity.Bank;
import uz.pdp.homework2.entity.BankomatResponsibleManager;
import uz.pdp.homework2.entity.User;
import uz.pdp.homework2.payload.ApiResponse;
import uz.pdp.homework2.payload.BankomatManagerDto;
import uz.pdp.homework2.repository.BankomatResposibleManagaerRepository;
import uz.pdp.homework2.repository.Bankrepository;
import uz.pdp.homework2.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class BankomatResponsibleManagerService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    BankomatResposibleManagaerRepository bankomatResposibleManagaerRepository;

    @Autowired
    Bankrepository bankrepository;

    @Autowired
    UserRepository userRepository;

    public ApiResponse addBanomatSettings(BankomatManagerDto bankomatManagerDto) {
        Optional<Bank> byIdBank = bankrepository.findById(bankomatManagerDto.getOwnerBankomat());
        Optional<User> byIdManager = userRepository.findById(bankomatManagerDto.getResponsibleManager());

        if (byIdBank.isPresent() && byIdManager.isPresent()) {
            Bank bank = byIdBank.get();
            User user = byIdManager.get();

            BankomatResponsibleManager bankomatResponsibleManager = new BankomatResponsibleManager();
            bankomatResponsibleManager.setMinimumSumInBank(bankomatManagerDto.getMinimumSumInBank());
            bankomatResponsibleManager.setMaximumSumInBank(bankomatManagerDto.getMaximumSumInBank());
            bankomatResponsibleManager.setResponsibleManager(user);
            bankomatResponsibleManager.setSumNow(bankomatManagerDto.getSumNow());
            bankomatResponsibleManager.setOwnerBankomat(bank);
            bankomatResposibleManagaerRepository.save(bankomatResponsibleManager);
            return new ApiResponse("Added", true);
        }
        return new ApiResponse("Error", false);

    }

    public ApiResponse getInfo(){
        List<BankomatResponsibleManager> all = bankomatResposibleManagaerRepository.findAll();
        for (BankomatResponsibleManager bankomatResponsibleManager : all) {
            Double sumNow = bankomatResponsibleManager.getSumNow();
            User responsibleManager = bankomatResponsibleManager.getResponsibleManager();
            if (sumNow< bankomatResponsibleManager.getMinimumSumInBank()){
                sendingEmail(responsibleManager.getEmail(), bankomatResponsibleManager.getId(), sumNow);
            }
        }
        return new ApiResponse("succes", true);
    }

    public void sendingEmail(String email, UUID bankomatId, Double sumNow) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Pixel@gmail.com");
            mailMessage.setTo(email);
            mailMessage.setSubject(bankomatId+" li bankomatda "+sumNow+"qoldi Iltimos tekshiring");
            javaMailSender.send(mailMessage);

        } catch (Exception e) {
            e.printStackTrace();

        }


    }
}
