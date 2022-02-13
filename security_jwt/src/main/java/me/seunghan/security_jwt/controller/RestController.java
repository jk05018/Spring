package me.seunghan.security_jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@GetMapping("/home")
	public String home() {
		return "<h1>HOME</h1>";
	}

	@PostMapping("/token")
	public String token(){
		return "<h1>TOKEN</h1>";
	}



}
