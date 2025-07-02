package com.carpooling.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @GetMapping("hello")
    public String greet(HttpServletRequest request){
        return "Hello World";
    }

}
