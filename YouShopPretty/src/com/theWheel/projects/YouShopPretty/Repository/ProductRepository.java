package com.theWheel.projects.YouShopPretty.Repository;

import java.util.ArrayList;
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
		List<Product> result = new ArrayList<>();
		String [] namePatterns = namePattern.trim().split(" ");
		for(String pattern : namePatterns) {
			TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE lower(p.name)"
					+ " LIKE :name OR lower(p.description) LIKE :description OR lower(p.tags) LIKE "
					+ ":tags OR lower(p.category) LIKE :category",Product.class);
			query.setParameter("name", "%" + pattern.trim().toLowerCase() + "%");
			query.setParameter("description", "%" + pattern.trim().toLowerCase() + "%");
			query.setParameter("tags", "%" + pattern.trim().toLowerCase() + "%");
			query.setParameter("category", "%" + pattern.trim().toLowerCase() + "%");
			result.addAll(query.getResultList());
		}
		return result;
	}
	
	public List<Product> getByPrinceRange(double min, double max){
		if(min <= 0 && max > 0 && max < min)
		{
			errors.put("Unexpected query param value", "Les valeurs de paramètres ne sont pas cohérentent");
			return new ArrayList<Product>();
		}
		TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.price BETWEEN :min AND :max",Product.class);
		query.setParameter("min", min);
		query.setParameter("max", max);
		return query.getResultList();
	}
	
	public List<Product> advanceQuery(String queryPattern, double min, double max){
		List<Product> result = new ArrayList<>();
		String [] queryPatterns = queryPattern.trim().split(" ");
		for(String pattern : queryPatterns) {
			TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE lower(p.name)"
					+ " LIKE :name OR lower(p.description) LIKE :description OR lower(p.tags) LIKE "
					+ ":tags OR lower(p.category) LIKE :category OR lower(p.caracteristics) LIKE "
					+ ":caracteristics",Product.class);
			query.setParameter("name", "%" + pattern.trim().toLowerCase() + "%");
			query.setParameter("description", "%" + pattern.trim().toLowerCase() + "%");
			query.setParameter("tags", "%" + pattern.trim().toLowerCase() + "%");
			query.setParameter("category", "%" + pattern.trim().toLowerCase() + "%");
			query.setParameter("caracteristics", "%" + pattern.trim().toLowerCase() + "%");
			result.addAll(query.getResultList());
		}
		if(min>=0 && max >0)
			result.addAll(getByPrinceRange(min, max));
		return result;
	}
	
	public void create(Product p) {
		errors.clear();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(p);
			et.commit();
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
			errors.put("Error", "Une erreur est survenue");
			et.rollback();
		}
	}
	
	public void update(Product p) {
		errors.clear();
		EntityTransaction et = em.getTransaction();
		Product product = getById(p.getId());
		product.setQuantity(p.getQuantity());
		try {
			et.begin();
			em.merge(product);
			et.commit();
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "Le produit n'existe pas ou a été retiré");
			et.rollback();
		}
	}

	public void delete(long id) {
		errors.clear();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			Product p = getById(id);
			em.remove(p);
			et.commit();
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "Le produit n'existe pas ou a été retiré");
			et.rollback();
		}
		catch (Exception e) {
			errors.put("Error", "Une erreur est survenue");
			et.rollback();
		}
	}
}
