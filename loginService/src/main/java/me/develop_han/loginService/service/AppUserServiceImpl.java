package me.develop_han.loginService.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

	private final AppUserRepository userRepository;
	private final RoleRepository roleRepository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userRepository.findByUsername(username);
		if(user == null){
			log.error("User not found int the database");
			throw new UsernameNotFoundException("user not found in the database");
		}else{
			log.info("user found int the database : {}", username);
		}

		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new User(user.getUsername(),user.getPassword(),authorities);

	}

	@Override
	public AppUser saveUser(AppUser user) {
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
