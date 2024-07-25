package com.takalobazar.admin.controller;

import com.takalobazar.admin.domain.CategoriesResponse;
import com.takalobazar.admin.domain.Category;
import com.takalobazar.admin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/index")
    public ModelMap listCategories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        ModelMap model = new ModelMap();
        CategoriesResponse response = categoryService.getCategories(page, size);
        model.addAttribute("categories", response.getCategories());
        model.addAttribute("totalPages", response.getTotalPages());
        model.addAttribute("currentPage", page);
        return model;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "categories/edit";
    }

    @GetMapping("/view/{id}")
    public String viewCategory(@PathVariable("id") Integer id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "categories/view";
    }

    @PostMapping("/edit")
    public String updateCategory(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes) {
        categoryService.updateCategory(category);
        redirectAttributes.addFlashAttribute("success", "Catégorie mise à jour avec succès !");
        return "redirect:/categories/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(
            @PathVariable("id") Integer id,
            @RequestParam(value = "confirm", required = false) Boolean confirm,
            RedirectAttributes redirectAttributes) {

        if (Boolean.TRUE.equals(confirm)) {
            try {
                categoryService.deleteCategory(id);
                redirectAttributes.addFlashAttribute("success", "Catégorie supprimée avec succès !");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("warning", "La suppression doit être confirmée.");
            return "redirect:/categories/confirm-delete/" + id; // Redirect to confirmation page
        }

        return "redirect:/categories/index";
    }

    @GetMapping("/insert")
    public ModelMap showInsertForm() {
        ModelMap model = new ModelMap();
        model.addAttribute("category", new Category());
        return model;
    }

    @PostMapping("/insert")
    public String insertCategory(@ModelAttribute("category") Category category, ModelMap model, RedirectAttributes redirectAttributes) {
        categoryService.saveCategory(category);
        model.clear();
        redirectAttributes.addFlashAttribute("success", "Catégorie créée avec succès !");
        return "redirect:/categories/index";
    }
}
