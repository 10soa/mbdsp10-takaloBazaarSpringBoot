package com.takalobazar.admin.controller;

import com.takalobazar.admin.service.CategoryService;
import com.takalobazar.admin.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "pages")
public class DashboardController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "dashboard")
    public ModelMap mmDashboard() {
        String response = categoryService.getCategories();
        System.out.println("****************************************************************");
        System.out.println(response);
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("categories", response);
        return modelMap;
    }

}
