package com.avecircle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.avecircle.service.DashboardService;

@Controller
public class DashboardController {
	
	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping("/dashboard")
	public String buildDashboard(Model model)
	{
		String quoteText = dashboardService.getQuote();
		model.addAttribute("quote", quoteText);
		return "dashboard";
	}


}
