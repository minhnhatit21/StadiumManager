package com.minhnhat.Quanlysanbong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class AccessController {
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

    @GetMapping("/home")
    public String homePage(Model model) {
//        model.addAttribute("title", "Card Title");
//        model.addAttribute("description", "This is the card description.");
        return "homePage";
    }
}
