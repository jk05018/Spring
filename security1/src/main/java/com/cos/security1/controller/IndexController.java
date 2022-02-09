package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping({"","/"})
	public String index(){
		// mustache의 기본 폴더는 src/main/resources/
		// -> ViewResolver 설정해줘야함 templates (prefix), .mustache (suffix)
		// 우리가 의존관계에 mustache를 사용하겠다고 지정해 줬으므로 application.yml에 prefix, suffix 생략가능.
		return "index";
	}
}
