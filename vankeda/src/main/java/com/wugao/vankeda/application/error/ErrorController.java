package com.wugao.vankeda.application.error;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ErrorController {
	
	@RequestMapping(value = "error/{errorCode}", method = RequestMethod.GET)
	public ModelAndView toErrorPage(@PathVariable("errorCode")String errorCode, String... message) {
		ModelAndView mav = new ModelAndView("error/" + errorCode);
		mav.addObject("errorMsg", message);
		return mav;
	}

}
