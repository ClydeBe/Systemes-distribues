package com.theWheel.projects.YouShopPretty.Provider;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import com.theWheel.projects.YouShopPretty.Entities.User;
import com.theWheel.projects.YouShopPretty.Repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

/**
 * This filter verify the access permissions for a user
 * based on username and passowrd provided in request
 * */
@Provider
public class AuthenticationFilter implements javax.ws.rs.container.ContainerRequestFilter
{

	@Context
	private ResourceInfo resourceInfo;

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final String AUTHENTICATION_NAME = "Bearer";

	@Override
	public void filter(ContainerRequestContext requestContext)
	{
		//Try to Inject
		UserRepository ur = new UserRepository();
		User user;
		String userRole;
		
		Method method = resourceInfo.getResourceMethod();
		//Ouvert à tous
		if( ! method.isAnnotationPresent(PermitAll.class))
		{
			//Bloqué à tous (Stupide mais utile dans le cas où le contenu sera accessible
			// à une date ultérieure)
			if(method.isAnnotationPresent(DenyAll.class))
			{
				requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
						.entity("Vous n'avez pas les droits pour acceder à cette ressource").build());
				return;
			}

			//Recupération de l'en tête et traitement de l'autorisation
			final MultivaluedMap<String, String> headers = requestContext.getHeaders();
			final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

			if(authorization == null || authorization.isEmpty())
			{
				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
						.entity("Votre rôle ne vous permet pas d'acceder à cette ressoucre").build());
				return;
			}

			String auth = authorization.get(0);
			if(auth.startsWith(AUTHENTICATION_NAME)){
				try {
					final String secretKey = "a7e9s5/@q4<aUz45.45dqXd;";
					String token = auth.replaceFirst(AUTHENTICATION_NAME + " ", "");
					Jws<Claims> jwsToken = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
					userRole = jwsToken.getBody().getSubject();
					
				} catch (Exception e) {
					e.printStackTrace();
					requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
				}
			}
			else {


				//On recupère décode l'authorisation
				final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
				String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));;

				//Recuperation des identifiants
				final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
				final String username = tokenizer.nextToken();
				final String password = tokenizer.nextToken();

				//Vérification des credentials
				
				user = new User();
				user.setUsername(username);
				user.setPassword(password);
				User userWithValidCredentials = ur.signin(user);
				if(userWithValidCredentials == null) {
					requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
							.entity("Invalid credentials! Votre rôle ne vous permet pas d'acceder "
									+ "à cette ressoucre").build());
					return;
				}
				else {
					userRole = getRole(user);
				}

				//Verification du rôle
				if(method.isAnnotationPresent(RolesAllowed.class))
				{
					RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
					Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

					//Is user valid?
					if( ! isUserAllowed(userRole, rolesSet))
					{
						requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
								.entity("Vous n'êtes pas autorisé à acceder à cette ressource").build());
						return;
					}
				}
			}
		}
	}
	private boolean isUserAllowed(String userRole, final Set<String> rolesSet)
	{
		boolean isAllowed = false;
		if(rolesSet.contains(userRole))
			isAllowed = true;
		return isAllowed;
	}
	
	private String getRole(User u){
		if(u.isSuperuser())
			return "ADMIN";
		if(u.isStaff())
			return "STAFF";
		return "CUSTOMER";
	}
}
