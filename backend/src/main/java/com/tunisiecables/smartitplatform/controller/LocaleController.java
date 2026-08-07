package com.tunisiecables.smartitplatform.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

@Controller
public class LocaleController {

    @GetMapping("/changeLang")
    public String changeLanguage(HttpServletRequest request, String lang) {
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if (localeResolver != null) {
            localeResolver.setLocale(request, null, new Locale(lang));
        }
        return "redirect:/";
    }
}