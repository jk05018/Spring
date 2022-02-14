package me.seunghan.security_jwt.controller;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import me.seunghan.security_jwt.model.User;
import me.seunghan.security_jwt.repository.UserRepository;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
public class RestController {

	private final BCryptPasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	@GetMapping("/")
	public String home() {
		return "<h1>HOME</h1>";
	}

	@PostMapping("/token")
	public String token() {
		return "<h1>TOKEN</h1>";
	}

	// user,manager,admin 접근 가능
	@GetMapping("/api/v1/user")
	public String user(){
		return "<h1>USER</h1>";
	}

	// manager, admin 접근 가능
	@GetMapping("/api/v1/manager")
	public String manager(){
		return "<h1>Manager</h1>";
	}


	@GetMapping("/api/v1/admin")
	public String admin(){
		return "<h1>Admin</h1>";
	}

	@GetMapping("/admin/users")
	public List<User> users() {
		return userRepository.findAll();
	}

	@PostMapping("/join")
	public String join(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles("ROLE_USER");
		userRepository.save(user);
		return "회원가입 완료";
	}

}
