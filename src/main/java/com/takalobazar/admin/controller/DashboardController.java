package com.takalobazar.admin.controller;

import com.takalobazar.admin.domain.APIResponse.CategoriesResponse;
import com.takalobazar.admin.domain.Dashboard;
import com.takalobazar.admin.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping(value = "")
    public String dashboard(Model model) {
        Dashboard response = dashboardService.getDashboard();
        model.addAttribute("ongoingExchanges", response.getOngoingExchanges());
        model.addAttribute("cancelledExchanges", response.getCancelledExchanges());
        model.addAttribute("acceptedExchanges", response.getAcceptedExchanges());
        model.addAttribute("refusedExchanges", response.getRefusedExchanges());
        model.addAttribute("exchangesBetweenDates", response.getExchangesBetweenDates());
        model.addAttribute("exchangesByUser", response.getExchangesByUser());
        model.addAttribute("objectsByCategory", response.getObjectsByCategory());
        return "dashboard/index";
    }

}
