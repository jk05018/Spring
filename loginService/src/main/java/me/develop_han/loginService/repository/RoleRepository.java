package me.develop_han.loginService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.develop_han.loginService.domain.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
	Role findByName(String name);
}
