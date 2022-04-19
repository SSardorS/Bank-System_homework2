package uz.pdp.homework2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.homework2.entity.Role;
import uz.pdp.homework2.entity.User;

import uz.pdp.homework2.payload.ApiResponse;
import uz.pdp.homework2.payload.LoginDto;
import uz.pdp.homework2.payload.RegisterDto;
import uz.pdp.homework2.repository.RoleRepository;
import uz.pdp.homework2.repository.UserRepository;
import uz.pdp.homework2.security.JwtProvider;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    public ApiResponse registerUser(RegisterDto registerDto) {
        boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());

        if (existsByEmail) {
            return new ApiResponse("This email is alredy exists", false);
        }

        Optional<Role> byId = roleRepository.findById(registerDto.getRoleId());

        if (!byId.isPresent())
            return new ApiResponse("This role is not found", false);

        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Role role = byId.get();
        user.setRoles(Collections.singleton(role));
        user.setEmailCode(UUID.randomUUID().toString());

        userRepository.save(user);

        boolean b = sendingEmail(user.getEmail(), user.getEmailCode());

        if (b)
            return new ApiResponse("Register is Success please verfy Email", true);
        return new ApiResponse("Register can not sending", false);


    }

    public boolean sendingEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Pixel@gmail.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Tasdiqlash");
            mailMessage.setText("<button><a href='http://localhost:8080/api/auth/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail + "'>tasdiqlash</a></button>");
            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> allByEmail = userRepository.findAllByEmail(username);
        if (allByEmail.isPresent())
            return allByEmail.get();
        throw new UsernameNotFoundException("user topilmadi");
    }

    public ApiResponse verifyEmail(String emailCode, String email) {
        Optional<User> byEmailAndEmailCode = userRepository.findByEmailAndEmailCode(email, emailCode);
        if (byEmailAndEmailCode.isPresent()) {
            User user = byEmailAndEmailCode.get();
            if (!user.isEnabled()) {
                user.setEnabled(true);
                user.setEmailCode(null);
                userRepository.save(user);
                return new ApiResponse("Akkaunt tasdiqlandi", true);
            }
            return new ApiResponse("Akkaunt tasdiqlangan", false);
        }
        return new ApiResponse("Akkauntga  berilgan kod hato", false);
    }

    public ApiResponse login(LoginDto loginDto) {

//            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
//            User user = (User) authenticate.getPrincipal();

        User user = (User) loadUserByUsername(loginDto.getEmail());
        String token = jwtProvider.generatedToken(user.getEmail(), user.getRoles());
        boolean matches = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());
        if (matches)
            return new ApiResponse("Token", true, token);
        return new ApiResponse("Parol yoki Login Hato", false);
    }


}
