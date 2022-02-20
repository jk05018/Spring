package me.seunghan.security_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.seunghan.security_jwt.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
	User findByUsername(String username);
}
