package com.theWheel.projects.YouShopPretty.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.theWheel.projects.YouShopPretty.Entities.Order;
import com.theWheel.projects.YouShopPretty.Entities.User;

public class OrderRepository {
	
	EntityManager em = EntityManagerProvider.getEntityManager();
	public Map<String, String> errors = new HashMap<String, String>();
	
	public OrderRepository() {
		
	}
	
	public List<Order> getAllOrders() {
		return em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
	}

	
	public Order getById(Long id) {
		return  em.find(Order.class, id);
	}
	
	public List<Order> getByUserId(Long id) {
		try {
			TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o WHERE o.userId=: userId", Order.class);
			query.setParameter("userId", id);
			return query.getResultList();
		}
		catch (Exception e) {
		}
		return null;
	}
	
	public List<Order> getByUsername(String username) {
		try {
			TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username=: username", User.class);
			query.setParameter("username", username);
			User u =  query.getSingleResult();
			return getByUserId(u.getId());
		}
		catch (Exception e) {
		}
		return null;
	}
	
	public boolean create(Order o) {
		errors.clear();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(o);
			et.commit();
			return true;
		}
		catch (EntityExistsException e) {
			errors.put("Entity_Exist", "Collision : Cette commande existe déjà");
			et.rollback();
		}
		catch(IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas une commande");
			et.rollback();
		}
		catch(Exception e) {
			errors.put("Error", "Une erreur est survenue");
			et.rollback();
		}
		return false;
	}
	
	public boolean update(Order o) {
		errors.clear();
		EntityTransaction et = em.getTransaction();
		Order order = getById(o.getId());
		order.setProcessed(o.isProcessed()); 
		try {
			et.begin();
			em.merge(order);
			et.commit();
			return true;
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "La commande n'existe pas ou a été retiré");
			et.rollback();
		}
		return false;
	}

	public boolean delete(long id) {
		errors.clear();
		EntityTransaction et = em.getTransaction();
		Order o = getById(id);
		try {
			et.begin();
			if (!em.contains(o)) {
				o = em.merge(o);
			}
			em.remove(o);
			et.commit();
			return true;
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "La commande n'existe pas ou a été retiré");
			et.rollback();
		}
		catch (Exception e) {
			errors.put("Error", "Une erreur est survenue");
			et.rollback();
		}
		return false;
	}
}
