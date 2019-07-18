package com.dayup.seckil.controller.api;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dayup.seckil.VO.userVO;
import com.dayup.seckil.base.controller.BaseApiController;
import com.dayup.seckil.base.result.Result;
import com.dayup.seckil.base.result.ResultCode;
import com.dayup.seckil.model.User;
import com.dayup.seckil.service.UserService;
import com.dayup.seckil.util.MD5util;
import com.dayup.seckil.util.UUIDUtil;
import com.dayup.seckil.util.ValidateCode;

import antlr.StringUtils;
import ch.qos.logback.classic.Logger;
import jdk.internal.org.jline.utils.Log;

@RestController
public class LoginApiController extends BaseApiController{

	private static Logger log = (Logger) LoggerFactory.getLogger(LoginApiController.class);

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login")
	public Result<Object> login(@ModelAttribute(value="user") @Valid User user, BindingResult bindingResult,HttpSession session, String code, Model model, HttpServletResponse response){
		log.info("username="+user.getUsername()+";password="+user.getPassword());
		if(bindingResult.hasErrors()){ 
			return Result.failure(); //500
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
				return Result.success(); //200
			}else{
				return  Result.failure(ResultCode.USER_LOGIN_ERROR);
			}
		}else{
			return  Result.failure(ResultCode.USER_LOGIN_ERROR);
		}
		
	}

}
