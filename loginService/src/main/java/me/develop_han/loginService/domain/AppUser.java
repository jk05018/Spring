package me.develop_han.loginService.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String username; // can be email either;
	private String password;

	@ManyToMany(fetch = FetchType.EAGER) // 주의하고 바꾸자
	private Collection<Role> roles = new ArrayList<>();
}
