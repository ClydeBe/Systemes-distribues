package com.theWheel.projects.YouShopPretty.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.theWheel.projects.YouShopPretty.Entities.User;

public class UserRepository {

	EntityManager em = EntityManagerProvider.getEntityManager();

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
		query.setParameter(1, username);
		return query.getSingleResult();
	}

	// find Users by email
	public User findByEmail(String email) {
		TypedQuery<User> query =  em.createQuery("SELECT u FROM User u WHERE u.email =: email" , User.class);
		query.setParameter("email", email);
		return  query.getSingleResult();
	}

	//insert a User in the database
	public void createUser(User user) {
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
			passwordEncryptor.setAlgorithm("SHA-256");
			passwordEncryptor.setPlainDigest( false );
			String password = passwordEncryptor.encryptPassword(user.getPassword());
			user.setPassword(password);
			em.persist(user);
			et.commit();
		}
		catch (Exception e) {
			et.rollback();
			e.printStackTrace();
		}
	}

	//update a User
	public void update(User u) {
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			em.merge(u);
			et.commit();
		} catch (Exception e) {
			et.rollback();
		}
	}

	//delete a User
	public void delete(User u) {
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			if (!em.contains(u)) {
				u = em.merge(u);
			}
			em.remove(u);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean usernameExist(String username) {
		String u = (String) em.createNativeQuery("SELECT username FROM user WHERE username =" + username).getSingleResult();
		return u == null;
	}

	/* Data validation */


	// validateEmail
	private void validateEmail( String email ) throws Exception {
		if ( email != null && email.trim().length() != 0 ) {
			if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
				throw new Exception( "Invalid email" );
			}
		} else {
			throw new Exception( "email field can't be empty" );
		}
	}

	// validatePassword
	private void validatePassword( String password1, String password2 ) throws Exception{
		if (password1 != null && password1.trim().length() != 0 && password2 != null && password2.trim().length() != 0) {
			if (!password1.equals(password2)) {
				throw new Exception("Passwords doesn't match");
			} else if (password1.trim().length() < 7) {
				throw new Exception("Password must have at least 8 caracters");
			}
		} else {
			throw new Exception("Password is required");
		}
	}
}
