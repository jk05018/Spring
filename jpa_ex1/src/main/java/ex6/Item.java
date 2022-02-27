package ex6;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn // 기본 : DTYPE
public class Item {

	@Id
	@GeneratedValue
	@Column(name = "item_id")
	private Long id;

	@ManyToMany(mappedBy = "items")
	private List<Category> categories = new ArrayList<>();

	private String name;

	private int price;

	private int stockQuantity;
}
