package com.theWheel.projects.YouShopPretty.Repository;

import java.util.List;

import javax.persistence.EntityManager;

import main.java.com.theWheel.projects.YouShopPretty.Entities.User;

public class UserRepository {

	
	static EntityManager em = EntityManagerProvider.getEntityManager();
	
	
	private UserRepository() {
	}
	
	public static List<User> getAllUsers() {
		return em.createQuery("SELECT u FROM user u", User.class).getResultList();
    }

    public static User findById(Long id) {
        return em.find(User.class, id);
    }

    public void create(User u) {
        em.persist(u);
    }

    public void update(User u) {
        em.merge(u);
    }

    public void delete(User u) {
        if (!em.contains(u)) {
            u = em.merge(u);
        }

        em.remove(u);
    }
}
