package com.example.amigossecurity.auth;

import com.example.amigossecurity.config.JwtService;
import com.example.amigossecurity.model.user.MyUser;
import com.example.amigossecurity.model.user.MyUserRepository;
import com.example.amigossecurity.model.user.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor

public class AuthenticationService {
    private final MyUserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;




    public AuthenticationResponse authenticate(AuthenticationRequest body) {
        log.info("Service layer of authenticate method ");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        body.getEmail(),
                    body.getPassword()
                )
        );
        log.info("After running authenticate method from auth manager this req should be authentiacted: {}", SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        // at this point user is authenticated

        var user =repository.findByEmail(body.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        log.info("This ist he token I am sending {} for user : {} in registeration", jwtToken, body.getEmail());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse register(RegisterRequest body) {
        log.info("Register request from the service layer ");
        var user = MyUser.builder()
                .firstName(body.getFirstName())
                .lastName(body.getLastName())
                .email(body.getUsername())
                .password(passwordEncoder.encode(body.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        log.info("This ist he token I am sending {} for user : {} in registeration", jwtToken, body.getUsername());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();


    }
}
