package me.develop_han.jpashop.domain;

import java.lang.annotation.Inherited;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@DiscriminatorValue("A")
public class Album extends Item{

	private String artist;
	private String etc;
}
