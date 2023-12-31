package com.example.amigossecurity.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationService authenticationService;
    @PostMapping("/registers")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest body
    ){
        log.info("Executing register request from the controller layer");
        return ResponseEntity.ok(authenticationService.register(body)) ;

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest body
    ){
        log.info("Executing authenticate request from the controller layer");

        return ResponseEntity.ok(authenticationService.authenticate(body)) ;
   }

}
