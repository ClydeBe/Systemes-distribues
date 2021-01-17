package com.theWheel.projects.YouShopPretty.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.theWheel.projects.YouShopPretty.Entities.User;

public class UserRepository {

	EntityManager em = EntityManagerProvider.getEntityManager();
	
	public UserRepository() {
	}
	
	public List<User> getAllUsers() {
		return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public User findById(Long id) {
        return  em.find(User.class, id);
    }

    public void create(User u) {
    	EntityTransaction et = em.getTransaction();
    	et.begin();
    	em.joinTransaction();
    	em.persist(u);
    	et.commit();
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
