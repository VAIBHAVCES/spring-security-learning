package com.example.amigossecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo/")
public class RestControllerApi {

    @GetMapping("/hello")
    public String hello(){
        return "Hello world";
    }

}
