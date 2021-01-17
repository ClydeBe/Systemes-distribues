package com.theWheel.projects.YouShopPretty.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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

	public void create(String email, String username, String password1, String password2) {
		EntityTransaction et = em.getTransaction();
		et.begin();
		User u = new User();
		try {
		em.joinTransaction();
		validateEmail(email);
		validatePassword(password1, password2);
		u.setUsername(username);
		u.setEmail(email);
		u.setPassword(password2);
		em.persist(u);
		et.commit();
		}
		catch (Exception e) {
			
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
