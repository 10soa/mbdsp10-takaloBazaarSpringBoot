package com.takalobazar.admin.controller;

import com.takalobazar.admin.config.UnauthorizedException;
import com.takalobazar.admin.domain.User;
import com.takalobazar.admin.domain.APIResponse.UsersResponse;
import com.takalobazar.admin.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.Base64;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/listUser")
    public String listUsers(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(required = false) String search,
                            @RequestParam(required = false) String gender,
                            @RequestParam(required = false) String type,
                            Model model) {
        UsersResponse usersResponse = userService.getUsers(page, size, search, gender, type);
        model.addAttribute("users", usersResponse.getUsers());
        model.addAttribute("currentPage", usersResponse.getCurrentPage());
        model.addAttribute("totalPages", usersResponse.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("search", search);
        model.addAttribute("gender", gender);
        model.addAttribute("type", type);
        return "users/index";
    }

    @GetMapping("/view/{id}")
    public String viewUser(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "users/view";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "users/edit";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user, @RequestParam("imageFile") MultipartFile imageFile, RedirectAttributes redirectAttributes) {
        try {
            String base64Image = null;
            if (imageFile != null && !imageFile.isEmpty()) {
                String contentType = imageFile.getContentType();
                base64Image = "data:" + contentType + ";base64," + Base64.getEncoder().encodeToString(imageFile.getBytes());
            }
            userService.updateUser(user, base64Image);

            redirectAttributes.addFlashAttribute("success", "Utilisateur mis à jour avec succès !");
        }
        catch (UnauthorizedException e) {
            throw e;
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la conversion de l'image : " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
        }
        return "redirect:/users/listUser";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("success", "User deleted successfully!");
        return "redirect:/users/listUser";
    }
}
