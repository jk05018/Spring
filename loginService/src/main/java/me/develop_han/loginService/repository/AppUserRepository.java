package me.develop_han.loginService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.develop_han.loginService.domain.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	AppUser findByUsername(String username);
}
