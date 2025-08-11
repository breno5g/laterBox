package dev.breno5g.laterbox.config;

import dev.breno5g.laterbox.config.exceptions.RestSecurityExceptionHandler;
import dev.breno5g.laterbox.config.exceptions.SecurityExceptions;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String authHeader = request.getHeader("Authorization");
            String token = authHeader.substring("Bearer ".length());
            JWTUserData user = tokenService.validateToken(token).orElseThrow();

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user, null, null));

        } catch (Exception e) {
            request.setAttribute(RestSecurityExceptionHandler.AUTH_ERROR_ATTRIBUTE,
                    SecurityExceptions.INVALID_OR_EMPTY_TOKEN_MSG);
        }

        filterChain.doFilter(request, response);
    }
}