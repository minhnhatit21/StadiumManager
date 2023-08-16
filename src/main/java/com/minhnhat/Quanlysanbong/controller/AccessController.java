package com.minhnhat.Quanlysanbong.controller;

import com.minhnhat.Quanlysanbong.models.User;
import com.minhnhat.Quanlysanbong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class AccessController {
    @Autowired
    UserService userService;
    @GetMapping("/login")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "registerPage";
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard";
    }

    @GetMapping("/stadiumManager")
    public String stadiumManagerPage() { return "stadiumManager";}

    @GetMapping("/beverageManager")
    @PreAuthorize("hasRole('ADMIN')")
    public String beverageManagerPage(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User exitingUser = userService.getUserID(username);
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("userID", exitingUser.getId());
        model.addAttribute("isLogin", true);
        return "beverageManager";
    }

    @GetMapping("/materialFacilities")
    @PreAuthorize("hasRole('ADMIN')")
    public String materialFacilitiesPage(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User exitingUser = userService.getUserID(username);
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("userID", exitingUser.getId());
        model.addAttribute("isLogin", true);
        return "materialFacilitiesPage";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
//        model.addAttribute("title", "Card Title");
//        model.addAttribute("description", "This is the card description.");
        return "homePage";
    }
}
