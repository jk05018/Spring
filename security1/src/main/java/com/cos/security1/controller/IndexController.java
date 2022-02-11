package com.cos.security1.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@GetMapping({"", "/"})
	public String index() {
		// mustache의 기본 폴더는 src/main/resources/
		// -> ViewResolver 설정해줘야함 templates (prefix), .mustache (suffix)
		// 우리가 의존관계에 mustache를 사용하겠다고 지정해 줬으므로 application.yml에 prefix, suffix 생략가능.
		return "index";
	}

	@ResponseBody
	@GetMapping("/user")
	public String user() {
		return "user";
	}

	@ResponseBody
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}

	@ResponseBody
	@GetMapping("/manager")
	public String manager() {
		return "manager";
	}

	// 스프링 시큐리티가 낚아채버 -> SecurityConfig 작성 후 낚아채지 않음
	// 그냥 security 읜존관계만 설정한다면 default로 낚아채는구나
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}

	@PostMapping("/join")
	public String join(User user) {
		log.info("User join start username : {}" , user.getUsername());
		user.setRole("ROLE_USER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user); // 이렇게 저장한다면 1234로 저장 -> 시큐리티로 로그인 할 수 없음 -> 이유는 패스워드가 암호화되어있기 문
		return "redirect:/loginForm";
	}

	@Secured("ROLE_ADMIN")
	@ResponseBody
	@GetMapping("/info")
	public String info(){
		return "개인정보";
	}

	@PreAuthorize("hasole('ROLE_MANAGER")
	@ResponseBody
	@GetMapping("/data")
	public String data(){
		return "데이터 정보";
	}
}
