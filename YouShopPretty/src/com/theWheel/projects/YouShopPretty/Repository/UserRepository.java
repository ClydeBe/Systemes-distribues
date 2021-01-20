package com.theWheel.projects.YouShopPretty.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.theWheel.projects.YouShopPretty.Entities.User;

@Stateless
@LocalBean
public class UserRepository {

	EntityManager em = EntityManagerProvider.getEntityManager();
	public Map<String, String> errors = new HashMap<String, String>();

	public UserRepository() {
	}

	//get all users
	public List<User> getAllUsers() {
		return em.createQuery("SELECT u FROM User u", User.class).getResultList();
	}

	//find Users by id
	public User findById(Long id) {
		return  em.find(User.class, id);
	}

	//find Users by username
	public User findByUsername(String  username) {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username =: username", User.class);
		query.setParameter("username", username);
		try{
			User u = query.getSingleResult();
			return u;
		}
		catch(NoResultException e) {
			return null ;
		}
		catch (Exception e) {
		}
		return new User();
	}
	
	public User signin(User u) {
		ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
		passwordEncryptor.setAlgorithm("SHA-256");
		passwordEncryptor.setPlainDigest( false );
		String password = u.getPassword();
		String username = u.getUsername();
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
		query.setParameter("username", username);
		try {
			User resultingUser = query.getSingleResult();
			if(passwordEncryptor.checkPassword(password, resultingUser.getPassword()))
				return resultingUser;
		}
		catch(NoResultException e){
		}
		return null;
	}

	// find Users by email
	public User findByEmail(String email) {
		TypedQuery<User> query =  em.createQuery("SELECT u FROM User u WHERE u.email =: email" , User.class);
		query.setParameter("email", email);
		try{
			 User u = query.getSingleResult();
			return u;
		}
		catch(NoResultException e) {
			return null ;
		}
		catch (Exception e) {
		}
		return new User();
	}

	//insert a User in the database
	public void createUser(User user) {
		EntityTransaction et = null;
		errors.clear();
		ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
		passwordEncryptor.setAlgorithm("SHA-256");
		passwordEncryptor.setPlainDigest( false );
		try {
			et = em.getTransaction();
			et.begin();
			String password = passwordEncryptor.encryptPassword(user.getPassword());
			user.setPassword(password);
			em.persist(user);
		}
		catch (EntityExistsException e) {
			errors.put("Entity_Exist", "Collision : Cet User éxiste déjà");
			et.rollback();
		}
		catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'objet ajouté n'est pas un User");
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

	//update a User
	public void update(User u) {
		EntityTransaction et = null;
		errors.clear();

		try {
			et = em.getTransaction();
			et.begin();
			em.merge(u);
		} catch (IllegalArgumentException e) {
			errors.put("Not_an_entity", "L'user n'existe pas ou a été retiré");
			et.rollback();
		}catch (Exception e) {
			errors.put("Error", "Une erreur est survenue");
			et.rollback();
		}
		finally {
			et.commit();
		}
	}

	//delete a User
	public void delete(User u) {
		EntityTransaction et = null;
		errors.clear();
		try {
			et = em.getTransaction();
			et.begin();
			if (!em.contains(u)) {
				u = em.merge(u);
			}
			em.remove(u);
		}catch (IllegalArgumentException e) {
			errors.put("Not_an_entity","L'utilisateur entré n'existe pas ou a été retiré");
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

	public boolean usernameExist(String username) {
		String u = (String) em.createNativeQuery("SELECT username FROM user WHERE username =" + username).getSingleResult();
		return u == null;
	}

}
