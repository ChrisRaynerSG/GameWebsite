package com.sparta.cr.carnasagameswebsite.controllers;

import com.sparta.cr.carnasagameswebsite.models.User;
import com.sparta.cr.carnasagameswebsite.service.SiteUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private SiteUserService siteUserService;

    public LoginController(SiteUserService siteUserService) {
        this.siteUserService = siteUserService;
    }

    @GetMapping("/login")
    public String login() {

        return "redirect:/#";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("user", user);
        siteUserService.createUser(user);
        return "/login";
    }
}
