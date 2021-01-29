package com.theWheel.projects.YouShopPretty.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.theWheel.projects.YouShopPretty.Entities.UserRole;

public class UserRoleRepository {
	
	EntityManager em = EntityManagerProvider.getEntityManager();
	
	public Map<String,String> errors = new HashMap<String,String>();
	
	public UserRoleRepository() {}
	
	public List<UserRole> getAllUserRole() {
		return em.createQuery("SELECT up FROM UserRole up",UserRole.class).getResultList();
	}
	
	public UserRole getUserRoleById(long id) {
		return em.find(UserRole.class, id);
	}
	
	public void createUserRole(UserRole userRole) {
		EntityTransaction et = null;
		errors.clear();
		try {
			et = em.getTransaction();
			et.begin();
			em.persist(userRole);
			et.commit();
		} catch (EntityExistsException e) {
			errors.put("Entity_Exist", "Collision : Cet Permission éxiste déjà");
			et.rollback();
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas un userRole");
			et.rollback();
		}
		catch (Exception e) {
			errors.put("Error", "Une erreur est survenue");
			et.rollback();
		}
	}
	
	public void updateUserRole(UserRole userRole) {
		EntityTransaction et = null;
		errors.clear();
		try {
			et = em.getTransaction();
			et.begin();
			em.merge(userRole);
			et.commit();
		} catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas une userRole");
			et.rollback();
		}
		catch (Exception e) {
			errors.put("Error", "Une erreur est survenue");
			et.rollback();
		}
	}
	
	public void deleteUserRole(UserRole userRole) {
		EntityTransaction et = null;
		errors.clear();
		try {
			et = em.getTransaction();
			et.begin();
			em.merge(userRole);
			et.commit();
		} catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas une userRole");
			et.rollback();
		}
		catch (Exception e) {
			errors.put("Error", "Une erreur est survenue");
			et.rollback();
		}
	}

}
