package ex6;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;

	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();

	private String name;
	private String city;
	private String street;
	private String zipcode;

}
