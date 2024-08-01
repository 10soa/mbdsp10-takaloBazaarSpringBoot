package com.takalobazar.admin.controller;

import com.takalobazar.admin.domain.Category;
import com.takalobazar.admin.domain.Exchange;
import com.takalobazar.admin.domain.User;
import com.takalobazar.admin.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Exchange")
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @GetMapping(value = "listeExchange")
    public String listExchange(@RequestParam Map<String, String> filters, Model model) {
        try {
            Map<String, Object> response = exchangeService.getExchanges(filters);
            List<Object> objects = (List<Object>) response.get("exchanges");
            Integer totalPages = (Integer) response.get("totalPages");
            String currentPageString = (String) response.get("currentPage");
            int currentPage;
            try {
                currentPage = Integer.parseInt(currentPageString);
            } catch (NumberFormatException e) {
                model.addAttribute("error", "Invalid number format for currentPage: " + e.getMessage());
                return "Exchange/listeExchange";
            }
            model.addAttribute("exchanges", objects);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", currentPage);
        } catch (IOException e) {
            model.addAttribute("error", "Failed to fetch Exchanges: " + e.getMessage());
        }
        return "Exchange/listeExchange";
    }

    @GetMapping(value = "view/{id}")
    public String getExchangeById(@PathVariable("id") Long id, Model model) {
        try {
            Map<String, Object> exchange = exchangeService.getExchangeById(id);
            model.addAttribute("exchange", exchange);
        } catch (IOException e) {
            model.addAttribute("error", "Failed to fetch Exchange: " + e.getMessage());
        }
        return "Exchange/view";
    }


}
