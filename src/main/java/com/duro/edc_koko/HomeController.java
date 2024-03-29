package com.duro.edc_koko;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "home/index";
    }

    @GetMapping("/authorized-only")
    public String auth() {
        return "Welcome! You are authorized";
    }

    @GetMapping("/userInfo")
    public ResponseEntity<String> getUserInfo(Authentication authentication) {
        Principal principal = (Principal) authentication.getPrincipal();
        return ResponseEntity.ok(principal.getName());
    }

}
