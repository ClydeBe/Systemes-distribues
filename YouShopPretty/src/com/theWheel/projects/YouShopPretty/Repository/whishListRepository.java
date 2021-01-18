package com.theWheel.projects.YouShopPretty.Repository;

import javax.json.Json;
import javax.persistence.EntityManager;

public class whishListRepository {
	
	EntityManager em = EntityManagerProvider.getEntityManager();
	
	public whishListRepository(){
		
	}

//	//Get all Products
//	public Json getAllProduct() {
//		return em.createQuery("SELECT p FROM ");
//	}
	
	
}
