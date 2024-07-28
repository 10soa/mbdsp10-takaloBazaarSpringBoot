package com.takalobazar.admin.controller;

import com.takalobazar.admin.domain.Category;
import com.takalobazar.admin.domain.User;
import com.takalobazar.admin.service.CategoryService;
import com.takalobazar.admin.service.ObjectService;
import com.takalobazar.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "pages/object")
public class ObjectController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectService objectService;

    @GetMapping(value = "create")
    public ModelMap modelCreate() {
        List<User> users = userService.findAllUsers();
        List<Category> categories = categoryService.findAllCategory();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("users", users);
        modelMap.addAttribute("categories", categories);
        return modelMap;
    }
    
    @PostMapping(value = "create")
    public String createObject(@RequestParam("name") String name,
                               @RequestParam("description") String description,
                               @RequestParam("category_id") Integer categoryId,
                               @RequestParam("image_file") MultipartFile imageFile,
                               @RequestParam("user_id") Integer userId,
                               ModelMap modelMap,
                               RedirectAttributes redirectAttributes) {
        try {
            List<User> users = userService.findAllUsers();
            List<Category> categories = categoryService.findAllCategory();
            modelMap.addAttribute("users", users);
            modelMap.addAttribute("categories", categories);
            Object createdObject = objectService.createObject(name, description, categoryId, imageFile, userId);
            modelMap.addAttribute("createdObject", createdObject);
            return "redirect:/pages/object/create";
        } catch (IOException e) {
            List<User> users = userService.findAllUsers();
            List<Category> categories = categoryService.findAllCategory();
            redirectAttributes.addFlashAttribute("users", users);
            redirectAttributes.addFlashAttribute("categories", categories);
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création de l'objet! Veuillez réessayer");
            return "redirect:/pages/object/create";
        }
    }
}
