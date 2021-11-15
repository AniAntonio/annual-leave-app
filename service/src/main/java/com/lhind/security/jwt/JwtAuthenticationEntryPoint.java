package com.lhind.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhind.exception.InvalidTokenException;
import com.lhind.util.CustomErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;


    @Override
    public void commence(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        CustomErrorResponse errorResponse = new CustomErrorResponse();
        HttpStatus status;
        List<String> errorMessage = new ArrayList<>();
        if (authException instanceof InvalidTokenException) {
            status = HttpStatus.UNAUTHORIZED;
            errorMessage.add(authException.getMessage());
            errorResponse.setMessage(Arrays.asList("InvalidTokenException"));
        } else {
            status = HttpStatus.FORBIDDEN;
            errorMessage.add(status.getReasonPhrase());
            errorResponse.setMessage(Arrays.asList(authException.getMessage()));
        }
        errorResponse.setStatus(status.value());
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), errorResponse);

    }
}
