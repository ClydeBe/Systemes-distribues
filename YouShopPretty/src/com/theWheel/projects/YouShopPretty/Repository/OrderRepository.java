package com.theWheel.projects.YouShopPretty.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.theWheel.projects.YouShopPretty.Entities.Order;

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
		TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o WHERE o.userId=: userId", Order.class);
		query.setParameter("userId", id);
		return query.getResultList();
	}
	
	public void create(Order o) {
		errors.clear();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(o);
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
		finally {
			et.commit();
		}
	}
	
	public void update(Order o) {
		errors.clear();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.merge(o);
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "La commande n'existe pas ou a été retiré");
			et.rollback();
		}
		finally {
			et.commit();
		}	
	}

	public void delete(Order o) {
		errors.clear();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			if (!em.contains(o)) {
				o = em.merge(o);
			}
			em.remove(o);
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "La commande n'existe pas ou a été retiré");
			et.rollback();
		}
		catch (Exception e) {
			errors.put("Error", "Une erreur est survenue");
			et.rollback();
		}
		finally {
			et.commit();
		}
	}
}
