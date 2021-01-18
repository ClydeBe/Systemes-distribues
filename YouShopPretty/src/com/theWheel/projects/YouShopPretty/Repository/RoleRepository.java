package com.theWheel.projects.YouShopPretty.Repository;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.theWheel.projects.YouShopPretty.Entities.Role;

public class RoleRepository {
	
	EntityManager em = EntityManagerProvider.getEntityManager();
	public Map<String, String> errors = new HashMap<String, String>();
	
	public RoleRepository() {}
	
	public void createRole(Role role) {
		EntityTransaction et = null;
		et.begin();
		
		try {
			em.persist(role);
		} catch (EntityExistsException e) {
			errors.put("Entity_Exist", "Collision : Ce Role éxiste déjà");
			et.rollback();
		}
		catch(IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas un role");
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

	public void updateRole(Role role) {
		EntityTransaction et = null;
		et.begin();
		
		try {
			em.merge(role);
		}catch(IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas un role ou a été rétiré");
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
	
	public void deleteRole(Role role) {
		EntityTransaction et = null;
		et.begin();
		
		try {
			em.remove(role);
		}catch(IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas un role ou a été rétiré");
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
