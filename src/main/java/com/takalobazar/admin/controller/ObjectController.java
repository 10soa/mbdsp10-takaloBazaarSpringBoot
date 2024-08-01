package com.takalobazar.admin.controller;

import com.takalobazar.admin.domain.Category;
import com.takalobazar.admin.domain.User;
import com.takalobazar.admin.service.CategoryService;
import com.takalobazar.admin.service.ObjectService;
import com.takalobazar.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    @GetMapping(value = "update")
    public ModelMap modelUpdate(@RequestParam("id") String id) {
        List<User> users = userService.findAllUsers();
        List<Category> categories = categoryService.findAllCategory();
        ModelMap modelMap = new ModelMap();
        try {
            Object objectDetails = objectService.getObjectById(id);
            modelMap.addAttribute("object", objectDetails);
        } catch (IOException e) {
            modelMap.addAttribute("error", "Erreur lors de la récupération des détails de l'objet!");
        }
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
            return "redirect:/pages/object/listeObject";
        } catch (IOException e) {
            List<User> users = userService.findAllUsers();
            List<Category> categories = categoryService.findAllCategory();
            redirectAttributes.addFlashAttribute("users", users);
            redirectAttributes.addFlashAttribute("categories", categories);
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création de l'objet! Veuillez réessayer");
            return "redirect:/pages/object/create";
        }
    }

    @PostMapping(value = "update")
    public String updateObject(@RequestParam String id,
                               @RequestParam String name,
                               @RequestParam String description,
                               @RequestParam Integer category_id,
                               @RequestParam(required = false) MultipartFile image_file,
                               ModelMap model) {
        try {
            objectService.updateObject(id, name, description, category_id,image_file);
            return "redirect:/pages/object/listeObject";
        } catch (IOException e) {
            model.addAttribute("error", "Failed to update object: " + e.getMessage());
            return "pages/object/update";
        }
    }

    @GetMapping(value = "listeObject")
    public String listObjects(@RequestParam Map<String, String> filters, Model model) {
        try {
            Map<String, Object> response = objectService.getObjects(filters);
            List<Object> objects = (List<Object>) response.get("objects");
            int totalPages = (int) response.get("totalPages");
            int currentPage = (int) response.get("currentPage");
            List<User> users = userService.findAllUsers();
            List<Category> categories = categoryService.findAllCategory();
            model.addAttribute("users", users);
            model.addAttribute("categories", categories);
            model.addAttribute("objects", objects);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", currentPage);
        } catch (IOException e) {
            List<User> users = userService.findAllUsers();
            List<Category> categories = categoryService.findAllCategory();
            model.addAttribute("users", users);
            model.addAttribute("categories", categories);
            model.addAttribute("error", "Failed to fetch objects: " + e.getMessage());
        }
        return "/pages/object/listeObject";
    }

    @GetMapping(value = "view")
    public ModelMap modelView(@RequestParam("id") String id) {
        ModelMap modelMap = new ModelMap();
        try {
            Object objectDetails = objectService.getObjectById(id);
            modelMap.addAttribute("object", objectDetails);
        } catch (IOException e) {
            modelMap.addAttribute("error", "Erreur lors de la récupération des détails de l'objet!");
        }
        return modelMap;
    }

    @GetMapping(value = "/delete/{id}")
    public String removeObject(@PathVariable("id") String objectId,
                               RedirectAttributes redirectAttributes,
                               @RequestParam(value = "confirm", required = false) Boolean confirm,
                               @RequestParam(value = "redirectUrl", required = false) String redirectUrl) {
        if (Boolean.TRUE.equals(confirm)) {
            try {
                objectService.removeObject(objectId);
                redirectAttributes.addFlashAttribute("message", "Objet supprimé avec succès !");
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", "Suppression interrompue : " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Veuillez confirmer la suppression de cet objet !");
        }
        return redirectUrl != null && !redirectUrl.isEmpty() ? "redirect:" + redirectUrl : "redirect:/pages/object/view?id=" + objectId;
    }

}

