package com.dayup.seckil.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dayup.seckil.model.User;
import com.dayup.seckil.service.UserService;
import com.dayup.seckil.util.MD5util;

@Controller
public class RegisterController {
	
	private static  Logger log = LoggerFactory.getLogger(RegisterController.class);

	
@Autowired
public UserService userService;

@RequestMapping(value = "/reg" , method = RequestMethod.GET)
public ModelAndView toRegister(ModelMap model) {
	User user = new User();
	return new ModelAndView("register").addObject(user);
}
@RequestMapping(value = "/register",method = RequestMethod.POST)
public ModelAndView register(@ModelAttribute(value="user") @Valid User user,BindingResult bindingResult) {
	log.info("username="+user.getUsername()+";password"+user.getPassword());
	if(bindingResult.hasErrors()) {
		return new ModelAndView("register");
	}
	String salt = "DJ";//盐
	String newPassword = MD5util.backtToDb(user.getPassword(), salt);//加盐
	user.setId(2019);
	user.setPassword(newPassword);//存储为密码
	user.setDbflag(salt);
	userService.regist(user);
	return new ModelAndView("login");
}
}
