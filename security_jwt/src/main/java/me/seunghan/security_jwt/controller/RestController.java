package me.seunghan.security_jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@GetMapping("/home")
	public String home() {
		return "<h1>HOME</h1>";
	}

}
