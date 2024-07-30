package com.takalobazar.admin.controller;

import com.takalobazar.admin.domain.APIResponse.TypeReportsResponse;
import com.takalobazar.admin.domain.Category;
import com.takalobazar.admin.domain.TypeReport;
import com.takalobazar.admin.service.TypeReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/typeReports")
public class TypeReportController {
    @Autowired
    private TypeReportService typeReportService;

    @GetMapping("")
    public String listTypeReports(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String q,
            Model model
    ) {
        TypeReportsResponse response = typeReportService.getTypeReports(page, size, q);
        model.addAttribute("typeReports", response.getTypeReports());
        model.addAttribute("totalPages", response.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("q", q);
        return "typeReports/index";

    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, @RequestParam(required = true) String name, Model model) {
        TypeReport typeReport = new TypeReport(id,name);
        model.addAttribute("typeReport", typeReport);
        return "typeReports/edit";
    }

    @PostMapping("/edit")
    public String updateTypeReport(@ModelAttribute("typeReport") TypeReport typeReport, RedirectAttributes redirectAttributes) {
        try{
            typeReportService.updateTypeReport(typeReport);
            redirectAttributes.addFlashAttribute("success", "Type de signalement mis à jour avec succès !");
            return "redirect:/typeReports";
        }
        catch(RuntimeException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/typeReports/edit/"+typeReport.getId()+"?name="+typeReport.getName();
    }

    @GetMapping("/delete/{id}")
    public String deleteTypeReport(
            @PathVariable("id") Integer id,
            @RequestParam(value = "confirm", required = false) Boolean confirm,
            RedirectAttributes redirectAttributes) {

        if (Boolean.TRUE.equals(confirm)) {
            try {
                typeReportService.deleteTypeReport(id);
                redirectAttributes.addFlashAttribute("success", "Type de signalement supprimé avec succès !");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("warning", "La suppression doit être confirmée.");
            return "redirect:/typeReports/confirm-delete/" + id;
        }

        return "redirect:/typeReports";
    }

    @GetMapping("/insert")
    public ModelMap showInsertForm() {
        ModelMap model = new ModelMap();
        model.addAttribute("typeReport", new TypeReport());
        return model;
    }

    @PostMapping("/insert")
    public String insertTypeReport(@ModelAttribute("typeReport") TypeReport typeReport, ModelMap model, RedirectAttributes redirectAttributes) {
        try{
            typeReportService.saveTypeReport(typeReport);
            model.clear();
            redirectAttributes.addFlashAttribute("success", "Type de signalement créé avec succès !");
            return "redirect:/typeReports";
        }
        catch(RuntimeException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/typeReports/insert";
    }
}
