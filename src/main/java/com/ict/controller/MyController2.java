package com.ict.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyController2 {
	@RequestMapping("/login")
	public ModelAndView loginCommand() {
		return new ModelAndView("login");
	}
	@RequestMapping("/logout")
	public ModelAndView logoutCommand(HttpServletRequest request) {
		request.getSession().invalidate();
		return new ModelAndView("redirect:/");
	}
}
