package com.theWheel.projects.YouShopPretty.Repository;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.theWheel.projects.YouShopPretty.Entities.Permission;

public class PermissionRepository {
	
	EntityManager em = EntityManagerProvider.getEntityManager();
	
	public Map<String, String> errors = new HashMap<String, String>();
	
	public PermissionRepository() {}
	
	public void createPermission(Permission permission) {
		EntityTransaction et = null;
		errors.clear();
		try {
			et = em.getTransaction();
			et.begin();
			em.persist(permission);
		}
		catch (EntityExistsException e) {
			errors.put("Entity_Exist", "Collision : Cet Permission éxiste déjà");
			et.rollback();
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas une Permission");
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
	
	public void updatePermission(Permission permission) {
		EntityTransaction et = null;
		errors.clear();
		try {
			et = em.getTransaction();
			et.begin();
			em.merge(permission);
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas une Permission");
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

	public void deletePermission(Permission permission) {
		EntityTransaction et = null;
		errors.clear();
		try {
			et = em.getTransaction();
			et.begin();
			em.remove(permission);
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas une Permission");
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


}
