package com.huafuepay.mobileapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huafuepay.common.web.controller.BaseController;

@Controller
@RequestMapping("/merchant")
public class LoginController extends BaseController {

	@RequestMapping("login")
	public String login() {
		System.out.println("##########测试MVC成功！～##########");
		return null;
	}
}
