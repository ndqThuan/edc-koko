package com.duro.edc_koko.auth.rest;

import com.duro.edc_koko.auth.model.AuthenticationRequest;
import com.duro.edc_koko.auth.model.AuthenticationResponse;
import com.duro.edc_koko.auth.model.RegisterRequest;
import com.duro.edc_koko.auth.service.AuthenticationService;
import com.duro.edc_koko.auth.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final LogoutService logoutService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest, HttpServletResponse httpResponse
    ) {
        return ResponseEntity.ok(service.register(httpResponse, registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse httpResponse
    ) {
        return ResponseEntity.ok(service.authenticate(httpResponse, authenticationRequest));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }


    @PostMapping("/logout")
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        logoutService.logout(request, response, null);
    }
}
