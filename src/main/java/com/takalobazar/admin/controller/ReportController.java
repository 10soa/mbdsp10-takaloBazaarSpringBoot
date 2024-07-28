package com.takalobazar.admin.controller;

import com.takalobazar.admin.config.UnauthorizedException;
import com.takalobazar.admin.domain.APIResponse.ReportDetailResponse;
import com.takalobazar.admin.domain.APIResponse.ReportResponse;
import com.takalobazar.admin.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/listReports")
    public String listReports(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int limit,
                              Model model) {
        try {
            ReportResponse response = reportService.getReports(page, limit);
            model.addAttribute("reports", response.getReports());
            model.addAttribute("currentPage", response.getCurrentPage());
            model.addAttribute("limit", limit);
            model.addAttribute("totalPages", response.getTotalPages());
        } catch (UnauthorizedException e) {
            throw e;
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "reports/index";
    }

    @GetMapping("/view/{id}")
    public String viewReportDetails(
            @PathVariable int id,
            @RequestParam(required = false) String created_at_start,
            @RequestParam(required = false) String created_at_end,
            @RequestParam(required = false) String reason,
            Model model) {
        try {
            ReportDetailResponse response = reportService.getReportDetails(id, created_at_start, created_at_end, reason);
            model.addAttribute("object", response.getObject());
            model.addAttribute("reports", response.getReports());
            model.addAttribute("currentPage", response.getCurrentPage());
            model.addAttribute("created_at_start", created_at_start);
            model.addAttribute("created_at_end", created_at_end);
            model.addAttribute("reason", reason);
            model.addAttribute("totalPages", response.getTotalPages());
        } catch (UnauthorizedException e) {
            throw e;
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "reports/view";
    }
}
