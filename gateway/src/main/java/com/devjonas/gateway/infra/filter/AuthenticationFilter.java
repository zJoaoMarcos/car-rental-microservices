package com.devjonas.gateway.infra.filter;

import com.devjonas.gateway.infra.provider.JWTProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class AuthenticationFilter implements HandlerInterceptor {

    private static final List<String> PUBLIC_ROUTES = List.of("/v1/auth");

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();

        if (PUBLIC_ROUTES.stream().anyMatch(path::startsWith)) {
            return true;
        }

        String header = request.getHeader("Authorization");

        if (header == null || header.isBlank()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String subject = jwtProvider.validateToken(header);

        if (subject.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }
}
