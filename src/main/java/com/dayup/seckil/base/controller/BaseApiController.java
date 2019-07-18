package com.dayup.seckil.base.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
@CrossOrigin(origins = "*",allowCredentials = "true")//用来处理跨域请求让你能访问不同域的文件
public class BaseApiController {
//定义所有api接口的 URL 前缀，并开放跨域访问权限, controller要以接口形式为前端调用，必须继承 BaseApiController
}
