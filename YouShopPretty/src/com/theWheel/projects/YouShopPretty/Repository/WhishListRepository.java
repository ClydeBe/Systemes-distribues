package com.theWheel.projects.YouShopPretty.Repository;

import javax.json.Json;
import javax.persistence.EntityManager;

public class WhishListRepository {
	
	EntityManager em = EntityManagerProvider.getEntityManager();
	
	public WhishListRepository(){
		
	}

//	//Get all Products
//	public Json getAllProduct() {
//		return em.createQuery("SELECT p FROM ");
//	}
	
	
}
