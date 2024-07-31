package com.takalobazar.admin.controller;

import com.takalobazar.admin.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/authentification")
public class AuthentificationController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/login")
    public String showLogin(Model model) {
        return "authentification/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        RedirectAttributes redirectAttributes) {
        try {
            boolean authenticated = authenticationService.login(username, password);
            if (authenticated) {
                // Set up session or other necessary actions
                return "redirect:/dashboard";
            } else {
                redirectAttributes.addFlashAttribute("loginError", "Erreur inattendu, veuillez nous contacter si cela persiste");
                return "redirect:/authentification/login";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("loginError", e.getMessage());
            return "redirect:/authentification/login";
        }
    }

    @PostMapping("/logout")
    public String logout() {
        authenticationService.logout();
        return "redirect:/authentification/login";
    }

}
