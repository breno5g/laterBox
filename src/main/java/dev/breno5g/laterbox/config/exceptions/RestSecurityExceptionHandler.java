package dev.breno5g.laterbox.config.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestSecurityExceptionHandler implements org.springframework.security.web.AuthenticationEntryPoint, org.springframework.security.web.access.AccessDeniedHandler {

    public static final String AUTH_ERROR_ATTRIBUTE = "AUTH_ERROR_MESSAGE";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String message = (String) request.getAttribute(AUTH_ERROR_ATTRIBUTE);
        if (message == null || message.isBlank()) {
            message = SecurityExceptions.INVALID_OR_EMPTY_TOKEN_MSG;
        }
        writeJson(response, HttpStatus.UNAUTHORIZED, message);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String message = accessDeniedException.getMessage();
        if (message == null || message.isBlank()) {
            message = "Access is denied";
        }
        writeJson(response, HttpStatus.FORBIDDEN, message);
    }

    private void writeJson(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Map<String, String> body = new HashMap<>();
        body.put("message", message);
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
