package com.duro.edc_koko.util;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CookieUtil {

    public String getTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equalsIgnoreCase("SESSION_EDC"))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElseThrow();
        }
        return null;
    }

    public void setTokenToCookie(HttpServletResponse response, String token) {
        ResponseCookie responseCookie = ResponseCookie.from("SESSION_EDC", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("strict")
                .maxAge(60 * 60)
                .build();
        response.addHeader("Set-Cookie", responseCookie.toString());
    }
}
