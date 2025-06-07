package com.tugas_besar.segor.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();

        // Allow login and register page without session
        if (uri.equals("/login") || uri.equals("/register") || uri.equals("/logout")) {
            return true;
        }

        // Check session
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/login");
            return false;
        }

        // Optionally, you can check role here
        // String role = (String) session.getAttribute("role");

        return true;
    }
}