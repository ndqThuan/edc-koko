package com.duro.edc_koko.auth.service;

import com.duro.edc_koko.auth.model.e_num.TokenType;
import com.duro.edc_koko.auth.repos.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt = jwtService.getTokenFromCookie(request, TokenType.ACCESS_TOKEN);

        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);

            jwtService.removeTokenFromCookie(response, TokenType.ACCESS_TOKEN);
            jwtService.removeTokenFromCookie(response, TokenType.REFRESH_TOKEN);

            SecurityContextHolder.clearContext();
        }
    }
}
