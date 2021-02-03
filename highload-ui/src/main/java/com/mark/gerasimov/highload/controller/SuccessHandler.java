package com.mark.gerasimov.highload.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SuccessHandler implements AuthenticationSuccessHandler {
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        final Object principal = authentication.getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        System.out.println("username: " + username);
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/u/368ebcbf-d092-4e6e-ba4b-4b37b2f9aa2f");
    }
}
