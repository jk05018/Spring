package me.develop_han.jpashop.domain;

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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.develop_han.jpashop.exception.NotEnoughStockException;

@Entity
@Getter @Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public class Item {

	@Id @GeneratedValue
	@Column(name = "item_id")
	private Long id;

	private String name;
	private int price;
	private int stockQuantity;

	@ManyToMany(mappedBy = "items")
	private List<Category> categories = new ArrayList<>();


	// ============ 비즈니스 로직 =============== //
	public void addStock(int quantity){
		this.stockQuantity += quantity;
	}

	public void removeStock(int quantity){
		int restStock = this.stockQuantity - quantity;
		if(restStock < 0){
			throw new NotEnoughStockException("[ERROR] 재고가 없습니다!");
		}
		this.stockQuantity = restStock;
	}
}
