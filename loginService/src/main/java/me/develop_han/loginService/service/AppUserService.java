package me.develop_han.loginService.service;

import java.util.List;

import me.develop_han.loginService.domain.AppUser;
import me.develop_han.loginService.domain.Role;

public interface AppUserService {
	AppUser saveUser(AppUser user);

	Role saveRole(Role role);

	void addRoleToUser(String username, String roleName);

	AppUser getUser(String username);

	// 실제로는 유저 전부를 출력하라 이런 경우는 없다 매우 비효율적
	// 이것보단 페이지 이런식으로 가지고 온다.
	List<AppUser> getUsers();

}
