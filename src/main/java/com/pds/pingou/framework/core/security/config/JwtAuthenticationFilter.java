package com.pds.pingou.framework.core.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filtro de autenticação JWT que intercepta requisições HTTP.
 * 
 * Esta classe faz parte do framework de segurança e é responsável por
 * validar tokens JWT e configurar o contexto de segurança do Spring.
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /** Endpoints públicos que não requerem autenticação */
    private static final List<String> PUBLIC_PATHS = List.of(
            "/v1/auth",
            "/api/v1/auth",
            "/v1/ai",
            "/api/v1/ai",
            "/v3/",
            "/swagger-ui",
            "/actuator"
    );

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getServletPath();

        // Permite acesso público aos endpoints configurados
        if (isPublicPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        filterChain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::contains);
    }
}
