package com.example.amigossecurity.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    @PostMapping("/registers")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest body
    ){
        return ResponseEntity.ok(authenticationService.register(body)) ;

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest body
    ){
        return ResponseEntity.ok(authenticationService.authenticate(body)) ;
   }

}
