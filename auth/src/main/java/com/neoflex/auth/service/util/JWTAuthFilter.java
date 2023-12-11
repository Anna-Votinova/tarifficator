package com.neoflex.auth.service.util;

import com.neoflex.auth.service.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader;
        String token;
        String login;

        authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer")) {
            response.sendError(HttpServletResponse.SC_CONFLICT,
                    "Заголовок не может быть пустым и должен начинаться с префикса Bearer");// нужно или нет
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);
        if (token.isBlank()) {
            response.sendError(HttpServletResponse.SC_CONFLICT,
                    "Токен не может быть пустым");
            filterChain.doFilter(request, response);
            return;
        }

        login = jwtProvider.extractLogin(token);
        if (Objects.isNull(login) || login.isBlank()) {
            response.sendError(HttpServletResponse.SC_CONFLICT,
                "У токена отсутствует логин");
            filterChain.doFilter(request, response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(login);
            boolean isTokenValid = jwtProvider.isTokenValid(token, userDetails);

            if (!isTokenValid) {
                response.sendError(HttpServletResponse.SC_CONFLICT,
                        "Токен невалиден");
                filterChain.doFilter(request, response);
                return;
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
            );

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
