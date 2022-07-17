package com.example.AccountService.security;

import com.example.AccountService.entity.SecurityEvent;
import com.example.AccountService.service.SecurityEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Autowired
    SecurityEventService securityEventService;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exc) throws IOException, ServletException {

        Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {

            LOGGER.warn("User: " + auth.getName()
                    + " attempted to access the protected URL: "
                    + request.getRequestURI());

            //log security event
//            this.securityEventRepository.save(new SecurityEvent(LocalDateTime.now(), "ACCESS_DENIED",
//                    auth.getName(), request.getRequestURI(), request.getRequestURI()));
            SecurityEvent se = new SecurityEvent();
            se.setDate(LocalDateTime.now());
            se.setAction("ACCESS_DENIED");
            se.setSubject(auth.getName());
            se.setObject(request.getRequestURI());
            se.setPath(request.getRequestURI());
            System.out.println(se);
            this.securityEventService.save(se);
        }

        response.sendError(HttpStatus.FORBIDDEN.value(), "Access Denied!");
    }
}