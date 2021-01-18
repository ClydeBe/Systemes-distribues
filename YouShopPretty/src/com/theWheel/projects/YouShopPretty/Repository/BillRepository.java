package com.theWheel.projects.YouShopPretty.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.theWheel.projects.YouShopPretty.Entities.Bill;

public class BillRepository {

	EntityManager em = EntityManagerProvider.getEntityManager();
	public Map<String, String> errors = new HashMap<String, String>();
	
	public BillRepository() {
		
	}
	
	public List<Bill> getAllBills() {
		return em.createQuery("SELECT b FROM Bill b", Bill.class).getResultList();
	}

	//find Users by id
	public Bill getById(Long id) {
		return  em.find(Bill.class, id);
	}
	
	public List<Bill> getByUserId(Long id) {
		TypedQuery<Bill> query = em.createQuery("SELECT b FROM Bill b WHERE b.userId=: id", Bill.class);
		query.setParameter(1, id);
		return query.getResultList();
	}
	
	public void create(Bill b) {
		errors.clear();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(b);
		}
		catch (EntityExistsException e) {
			errors.put("Entity_Exist", "Collision : cette facture existe déjà");
			et.rollback();
		}
		catch(IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas une facture");
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
	
	public void update(Bill b) {
		errors.clear();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.merge(b);
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "La facture n'existe pas ou a été retiré");
			et.rollback();
		}
		finally {
			et.commit();
		}
		
	}

	public void delete(Bill b) {
		errors.clear();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			if (!em.contains(b)) {
				b = em.merge(b);
			}
			em.remove(b);
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "La facture n'existe pas ou a été retiré");
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
