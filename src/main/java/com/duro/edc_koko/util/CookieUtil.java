package com.duro.edc_koko.util;


import com.duro.edc_koko.auth.model.e_num.TokenType;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CookieUtil {

    public String getTokenFromCookie(HttpServletRequest request, TokenType tokenType) {
        String cookieName = tokenType.getLabel();

        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equalsIgnoreCase(cookieName))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElseThrow();
        }
        return null;
    }

    public void setTokenToCookie(HttpServletResponse response, TokenType tokenType, String token) {
        String cookieName = tokenType.getLabel();

        ResponseCookie responseCookie = ResponseCookie.from(cookieName, token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("strict")
                .maxAge(60 * 60)
                .build();
        response.addHeader("Set-Cookie", responseCookie.toString());
    }
}
