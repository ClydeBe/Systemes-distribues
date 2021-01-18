package com.theWheel.projects.YouShopPretty.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProvider {
	
	public static EntityManager entityManager;
	
	public static EntityManager getEntityManager() {
		if(entityManager == null) {
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU");
		 entityManager = emf.createEntityManager();
		}
		return entityManager;
	}
}
