package me.develop_han.loginService.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.develop_han.loginService.domain.AppUser;
import me.develop_han.loginService.service.AppUserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppUserResource {

	private final AppUserService userService;


	@GetMapping("/users")
	public ResponseEntity<List<AppUser>> getUsers(){
		return ResponseEntity.ok().body(userService.getUsers());
	}




}
