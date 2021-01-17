package com.theWheel.projects.YouShopPretty.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.theWheel.projects.YouShopPretty.Entities.User;

public class UserRepository {

	EntityManager em = EntityManagerProvider.getEntityManager();

	public UserRepository() {
	}

	public List<User> getAllUsers() {
		return em.createQuery("SELECT u FROM User u", User.class).getResultList();
	}

	public User findById(Long id) {
		return  em.find(User.class, id);
	}

	public void create(User user) {
		EntityTransaction et;
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
			e.printStackTrace();
		}
	}

	public void update(User u) {
		em.merge(u);
	}

	public void delete(User u) {
		if (!em.contains(u)) {
			u = em.merge(u);
		}

		em.remove(u);
	}
	
	public boolean usernameExist(String username) {
		String u = (String) em.createNativeQuery("SELECT username FROM user WHERE username =" + username).getSingleResult();
		return u == null;
	}

	//Data validation

	private void validateEmail( String email ) throws Exception {
		if ( email != null && email.trim().length() != 0 ) {
			if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
				throw new Exception( "Invalid email" );
			}
		} else {
			throw new Exception( "email field can't be empty" );
		}
	}


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
