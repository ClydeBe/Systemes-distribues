package com.theWheel.projects.YouShopPretty.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.theWheel.projects.YouShopPretty.Entities.Product;

public class ProductRepository {
	
	EntityManager em =EntityManagerProvider.getEntityManager();
	public Map<String, String> errors = new HashMap<String, String>();
	
	public ProductRepository() {
		
	}
	
	public List<Product> getAllProducts(){
		return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
	}
	
	public Product getById(Long Id) {
		return em.find(Product.class, Id);
	}
	
	public List<Product> getByName(String namePattern){
		TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE lower(p.name) LIKE :name",Product.class);
		query.setParameter(1, namePattern.toLowerCase());
		return query.getResultList();
	}
	
	public List<Product> getByPrinceRange(int min, int max){
		TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.price > :min and p.price < :max",Product.class);
		query.setParameter(1, min);
		query.setParameter(2, max);
		return query.getResultList();
	}
	
	public void create(Product p) {
		errors.clear();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(p);
		}
		catch (EntityExistsException e) {
			errors.put("Entity_Exist", "Ce produit existe déjà");
			et.rollback();
		}
		catch(IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas un produit");
			et.rollback();
		}
		catch(Exception e) {
			errors.put("Error", "Une erruer est survenue");
			et.rollback();
		}
		finally {
			et.commit();
		}
	}
	
	public void update(Product p) {
		errors.clear();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.merge(p);
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "Le produit n'existe pas ou a été retiré");
			et.rollback();
		}
		finally {
			et.commit();
		}
		
	}

	public void delete(Product p) {
		errors.clear();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			if (!em.contains(p)) {
				p = em.merge(p);
			}
			em.remove(p);
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "Le produit n'existe pas ou a été retiré");
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
