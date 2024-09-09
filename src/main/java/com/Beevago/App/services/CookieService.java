package com.Beevago.App.services;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CookieService {

    public static void setCookie(HttpServletResponse response, String key, String value, int time) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(time);
        response.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest request, String key) {
        return Optional.ofNullable(request.getCookies())
        .flatMap(cookies -> Arrays.stream(cookies)
        .filter(cookie -> key.equals(cookie.getName()))
        .findAny()
        ).map(e -> e.getValue())
        .orElse(null);
    }

}
