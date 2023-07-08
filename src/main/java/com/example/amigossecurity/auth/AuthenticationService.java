package com.example.amigossecurity.auth;

import com.example.amigossecurity.config.JwtService;
import com.example.amigossecurity.model.user.MyUser;
import com.example.amigossecurity.model.user.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthenticationService {
    private final MyUserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;



    public AuthenticationResponse authenticate(AuthenticationRequest body) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        body.getEmail(),
                    body.getPassword()
                )
        );
        // at this point user is authenticated

        var user =repository.findByEmail(body.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse register(RegisterRequest body) {
        var user = MyUser.builder()
                .firstName(body.getFirstName())
                .lastName(body.getLastName())
                .email(body.getUsername())
                .password(passwordEncoder.encode(body.getPassword()))
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
