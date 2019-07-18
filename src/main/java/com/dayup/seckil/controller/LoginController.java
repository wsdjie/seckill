package com.dayup.seckil.controller;

import java.io.IOException;
import java.security.PublicKey;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dayup.seckil.VO.userVO;
import com.dayup.seckil.model.User;
import com.dayup.seckil.service.UserService;
import com.dayup.seckil.util.MD5util;
import com.dayup.seckil.util.UUIDUtil;
import com.dayup.seckil.util.ValidateCode;

import antlr.StringUtils;
import ch.qos.logback.classic.Logger;
import jdk.internal.org.jline.utils.Log;

@Controller
public class LoginController {

	private static Logger log = (Logger) LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;
/**
 * 跳转到登录页面
 * @param model
 * @return
 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("title", "登录页面");
		return "login";
	}
/**
 * 登录验证
 * @param user
 * @param bindingResult 
 * @param session
 * @param code 用户输入的验证码
 * @param model
 * @param response
 * @return
 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@ModelAttribute(value="user") @Valid User user, BindingResult bindingResult,HttpSession session, String code, Model model, HttpServletResponse response){
		log.info("username="+user.getUsername()+";password="+user.getPassword());
		if(bindingResult.hasErrors()){ //如果有数据校验错误，返回登录的页面
			return "login";
		}
		String sessionCode = (String) session.getAttribute("code");
		if (!org.thymeleaf.util.StringUtils.equalsIgnoreCase(code, sessionCode)) { //验证码不匹配返回登录页面
			model.addAttribute("message", "验证码不匹配");
			return "login";
		}
		
		userVO dbUser = userService.getUser(user.getUsername()); //根据用户名获取数据库中的账号信息
		
		if(dbUser != null){
			if(dbUser.getPassword().equals(MD5util.inputToDb(user.getPassword(), dbUser.getDbflag()))){ //将用户输入的密码进行加密后对比数据库中的密码
				//session.setAttribute("user", dbUser);
				String token = UUIDUtil.getUUID(); //生成用户标识码
				userService.saveUserToRedisByToken(dbUser, token); //将用户数据和标识码上传到redis
				System.out.println("token===== "+token);
				Cookie cookie = new Cookie("token", token); //创建一个Cookie保存用户标识码
				cookie.setMaxAge(3600);//有限期为1小时
				cookie.setPath("/");
				response.addCookie(cookie);//添加Cookie
				return "redirect:/home"; //重定向到指定的@RequestMapping的请求路径
			}else{
				return "login";
			}
		}else{
			return "login";
		}
		
	}
	
/**
 * 验证码
 * @param request
 * @param response
 * @return
 * @throws IOException
 */
	@RequestMapping(value = "/validateCode")
	public String validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置响应类型格式为图片格式
		response.setContentType("image/jpeg");
		// 禁止图像缓存
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		HttpSession session = request.getSession();

		ValidateCode vCode = new ValidateCode(120, 34, 5, 100);
		session.setAttribute("code", vCode.getCode());
		vCode.write(response.getOutputStream());
		return null;
	}

}
