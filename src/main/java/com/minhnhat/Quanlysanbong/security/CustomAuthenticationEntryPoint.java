package com.minhnhat.Quanlysanbong.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private SpringTemplateEngine templateEngine;

    public CustomAuthenticationEntryPoint(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Create a Thymeleaf context
        WebContext context = new WebContext(request, response, request.getServletContext());

        // Add the exception message to the context
        context.setVariable("exceptionMessage", exception.getMessage());

        // Render the Thymeleaf template
        String html = templateEngine.process("unauthorized.html", context);

        // Write the HTML to the response
        response.getWriter().write(html);
    }
}
