package com.wugao.vankeda.application.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AdminController {
	
	@RequestMapping(value = "v/admin/home", method = RequestMethod.GET)
	public ModelAndView toAdminHomePage() {
		return new ModelAndView("admin/home");
	}
	
	@RequestMapping(value = "v/admin/activity", method = RequestMethod.GET)
	public ModelAndView toActivityPage() {
		return new ModelAndView("admin/activity");
	}

}
