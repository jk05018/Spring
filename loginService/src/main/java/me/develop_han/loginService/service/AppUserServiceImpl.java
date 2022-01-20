package me.develop_han.loginService.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.develop_han.loginService.domain.AppUser;
import me.develop_han.loginService.domain.Role;
import me.develop_han.loginService.repository.AppUserRepository;
import me.develop_han.loginService.repository.RoleRepository;

@Slf4j // for logging
@Service
@Transactional
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService{

	private final AppUserRepository userRepository;
	private final RoleRepository roleRepository;


	@Override
	public AppUser save(AppUser user) {
		log.info("Saving new user {} to the database",user.getUsername());
		return userRepository.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		log.info("Saving new Role {} to the database",role.getName());
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		log.info("Adding role {} to the user {} ",roleName,username);
		AppUser user = userRepository.findByUsername(username);
		Role role = roleRepository.findByName(roleName);
		user.getRoles().add(role);
	}

	@Override
	public AppUser getUser(String username) {
		log.info("fetching user {}", username);
		return userRepository.findByUsername(username);
	}

	@Override
	public List<AppUser> getUsers() {
		log.info("fetching users");
		return userRepository.findAll();
	}
}
