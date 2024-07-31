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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping(value = "")
    public String dashboard(@RequestParam(required = false) String date1,
                            @RequestParam(required = false) String date2,
                            @RequestParam(required = false) String status,
                            Model model) {
        Dashboard response = dashboardService.getDashboard(date1,date2,status);
        model.addAttribute("ongoingExchanges", response.getOngoingExchanges());
        model.addAttribute("cancelledExchanges", response.getCancelledExchanges());
        model.addAttribute("acceptedExchanges", response.getAcceptedExchanges());
        model.addAttribute("refusedExchanges", response.getRefusedExchanges());
        model.addAttribute("exchangesBetweenDates", response.getExchangesBetweenDates());
        model.addAttribute("exchangesByUser", response.getExchangesByUser());
        model.addAttribute("objectsByCategory", response.getObjectsByCategory());
        model.addAttribute("date1", date1);
        model.addAttribute("date2", date2);
        model.addAttribute("status", status);

        return "dashboard/index";
    }

}
