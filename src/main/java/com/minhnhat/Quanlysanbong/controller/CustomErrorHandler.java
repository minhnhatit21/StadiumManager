package com.minhnhat.Quanlysanbong.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CustomErrorHandler implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, HttpServletResponse response) {
        // Get the error code from the request.
        int statusCode = response.getStatus();
        System.out.println("Code: " + statusCode);
        // Handle the error based on the status code.
        switch (statusCode) {
            case 401:
                return "loginPage";
            case 403:
                return "error-403";
            case 404:
                return "error-404";
            case 500:
                return "error-500";
            default:
                return "error";
        }
    }

//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }
}
