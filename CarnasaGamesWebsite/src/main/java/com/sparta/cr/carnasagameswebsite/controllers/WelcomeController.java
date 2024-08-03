package com.sparta.cr.carnasagameswebsite.controllers;

import com.sparta.cr.carnasagameswebsite.service.SiteUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    private final SiteUserService siteUserService;

    public WelcomeController(SiteUserService siteUserService) {
        this.siteUserService = siteUserService;
    }

    @GetMapping("/")
    public String welcomePage(Model model) {
        return "index";
    }
    @GetMapping("/index")
    public String index(Model model) {
        return "index";
    }

}
