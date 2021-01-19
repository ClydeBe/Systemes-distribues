package com.theWheel.projects.YouShopPretty.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.theWheel.projects.YouShopPretty.Entities.RolePermission;
import com.theWheel.projects.YouShopPretty.Entities.UserRole;

public class RolePermissionRepository {
	
EntityManager em = EntityManagerProvider.getEntityManager();
	
	Map<String,String> errors = new HashMap<String,String>();
	
	public RolePermissionRepository() {}
	
	public List<UserRole> getAllUserRole() {
		return em.createQuery("SELECT up FROM UserRole up",UserRole.class).getResultList();
	}
	
	public UserRole getUserRoleById(long id) {
		return em.find(UserRole.class, id);
	}
	
	public void createRolePermission(RolePermission rolePermission) {
		EntityTransaction et = null;
		errors.clear();
		try {
			et = em.getTransaction();
			et.begin();
			em.persist(rolePermission);
		} catch (EntityExistsException e) {
			errors.put("Entity_Exist", "Collision : Cet Permission éxiste déjà");
			et.rollback();
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas un rolePermission");
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
	
	public void updatePermission(RolePermission rolePermission) {
		EntityTransaction et = null;
		errors.clear();
		try {
			et = em.getTransaction();
			et.begin();
			em.merge(rolePermission);
		} catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas une rolePermission");
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
	
	public void deletePermission(RolePermission rolePermission) {
		EntityTransaction et = null;
		errors.clear();
		try {
			et = em.getTransaction();
			et.begin();
			em.merge(rolePermission);
		} catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas une rolePermission");
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
