package com.theWheel.projects.YouShopPretty.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.theWheel.projects.YouShopPretty.Entities.Bill;
import com.theWheel.projects.YouShopPretty.Entities.Comment;

public class CommentRepository {
	
	EntityManager em = EntityManagerProvider.getEntityManager();
	public Map<String, String> errors = new HashMap<String, String>();
	
	public CommentRepository() {
		
	}
	
	public List<Comment> getByProductId(Long Id){
		TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c WHERE c.product_id =: Id", Comment.class);
		query.setParameter("Id", Id);
		List<Comment> comments = query.getResultList();
		return comments;
	}
	
	public List<Comment> getByUserId(Long Id){
		TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c WHERE c.user_id =: Id", Comment.class);
		query.setParameter("Id", Id);
		List<Comment> comments = query.getResultList();
		return comments;
	}
	
	public void addComment(Comment c) {
		errors.clear();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(c);
			et.commit();
		}
		catch (EntityExistsException e) {
			errors.put("Entity_Exist", "Collision!");
			et.rollback();
		}
		catch(IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas un commentaire ou n'est pas valide");
			et.rollback();
		}
		catch(Exception e) {
			errors.put("Error", "Une erreur est survenue");
			et.rollback();
		}
	}
	
	public void update(Comment c) {
		errors.clear();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.merge(c);
			et.commit();
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "Le commentaire n'existe pas ou a été retiré");
			et.rollback();
		}
		catch (Exception e) {
			errors.put("Error", "Une erreur est survenue");
			et.rollback();
		}
	}

	public void delete(Comment c) {
		errors.clear();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			if (!em.contains(c)) {
				c = em.merge(c);
			}
			em.remove(c);
			et.commit();
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "Le commentaire n'existe pas ou a déjàO été retiré");
			et.rollback();
		}
		catch (Exception e) {
			errors.put("Error", "Une erreur est survenue");
			et.rollback();
		}
	}
}
