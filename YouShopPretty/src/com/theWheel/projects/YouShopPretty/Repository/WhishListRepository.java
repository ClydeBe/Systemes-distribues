package com.theWheel.projects.YouShopPretty.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.theWheel.projects.YouShopPretty.Entities.Whishlist;

public class WhishListRepository {
	
//	EntityManager of the whisList
	EntityManager em = EntityManagerProvider.getEntityManager();
//	Collection of errors
	public Map<String, String> errors = new HashMap<String, String>();

	// Constructor	
	public WhishListRepository(){
		
		
	}
	
	// Create a WhisList
	public void createWhisList(Whishlist whishlist) {
		EntityTransaction et = null;
		errors.clear();
		try {
			et = em.getTransaction();
			et.begin();
			em.persist(whishlist);
			et.commit();
		}
		catch (EntityExistsException e) {
			errors.put("Entity_Exist", "Collision : Cette whishlist éxiste déjà");
			et.rollback();
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas une whishlist");
			et.rollback();
		}
		catch(Exception e) {
			errors.put("Error", "Une erreur est survenue");
			et.rollback();
		}
	}
	
	// Update a WhisList
	public void updateWhisList(Whishlist whishlist) {
		EntityTransaction et = null;
		errors.clear();
		try {
			et = em.getTransaction();
			et.begin();
			em.merge(whishlist);
			et.commit();
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas une whishlist ou n'exite pas");
			et.rollback();
		}
		catch(Exception e) {
			errors.put("Error", "Une erreur est survenue");
			et.rollback();
		}
	}
	
	public void deleteWhisList(Whishlist whishlist) {
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			if(em.contains(whishlist))
				em.remove(whishlist);
			et.commit();
		} catch (IllegalArgumentException e) {
			errors.put("Not_an_entity","L'utilisateur entré n'existe pas ou a été retiré");
			et.rollback();
		}
		catch (Exception e) {
			errors.put("Error", "Une erreur est survenue");
			et.rollback();
		}
	}
	
	// Get a WhisList by her id
	public Whishlist getWhishList(long id) {
		return em.find(Whishlist.class, id);
	}
	
	// Get a WhisList by her id
	public  List<Whishlist> getAllWhishList() {
		return em.createQuery("SELECT w FROM Whishlist w",Whishlist.class).getResultList();
	}
	
	// Get a WhisList by the user id
	public Whishlist getWhishListByUserId(long idUser) {
		TypedQuery<Whishlist> query = em.createQuery("SELECT w FROM Whishlist w WHERE w.user_id =: idUser",Whishlist.class);
		query.setParameter("1", idUser);
		return query.getSingleResult();
	}

//	//Get all Products
//	public Json getAllProduct() {
//		return em.createQuery("SELECT p FROM ");
//	}
	
	
}
