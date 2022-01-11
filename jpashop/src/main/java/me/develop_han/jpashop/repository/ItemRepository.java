package me.develop_han.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import me.develop_han.jpashop.domain.Item;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

	private final EntityManager em;

	public void save(Item item){
		if(item.getId() == null){
			em.persist(item);
		}else{
			em.merge(item);
		}
	}

	public Item findOne(Long id){
		return em.find(Item.class,id);
	}

	public List<Item> findAll(){
		return em.createQuery("select i from Item i", Item.class).getResultList();
	}
}
