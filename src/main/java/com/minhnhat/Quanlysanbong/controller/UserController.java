package com.minhnhat.Quanlysanbong.controller;

import com.minhnhat.Quanlysanbong.models.Stadium;
import com.minhnhat.Quanlysanbong.models.User;
import com.minhnhat.Quanlysanbong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/info")
public class UserController {

    @Autowired
    UserService userService;
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String getUserInfo(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User exitingUser = userService.getUserID(username);
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("userID", exitingUser.getId());
        model.addAttribute("isLogin", true);
        return "homePage";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminInfo() {
        return "stadiumManager";
    }


}

