package com.theWheel.projects.YouShopPretty;

import java.util.Date;
import java.util.Properties;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.theWheel.projects.YouShopPretty.Entities.User;
import com.theWheel.projects.YouShopPretty.Entities.UserRole;
import com.theWheel.projects.YouShopPretty.Repository.UserRepository;
import com.theWheel.projects.YouShopPretty.Repository.UserRoleRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserResource {

	@Context
	UriInfo uri;
	
	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_NAME = "Bearer ";
	private static final String COOKIE_NAME = "YSPsessionId";
	private static final String secretKey = "a7e9s5/@q4<aUz45.45dqXd;";
	
	UserRepository userRepository = new UserRepository();

	@PermitAll
	@GET
	@Path("print")
	public Response doTest() {
		User u = new User();
		u.setUsername("ClydeBe");
		u.setEmail("areuclide@gmail.com");
		sendResetPasswordEmail(u);
		return Response.ok().build();
	}

	@PermitAll
	@GET
	@Path("test/{email}")
	public Response sendEmail(@PathParam("email") String email) {
		sendWelcomeEmail(email);
		return Response.ok().build();
	}
	
	@RolesAllowed({"STAFF", "ADMIN"})
	@GET
	public Response AllUsers() {
		return Response.ok(userRepository.getAllUsers()).build();
	}

	@RolesAllowed({"STAFF", "ADMIN"})
	@GET
	@Path("{id}")
	public Response singleUserById(@PathParam("id") Long id) {
		User user;
		user = userRepository.findById(id);
		if(user == null)
			return Response.noContent().build();
		return Response.ok(user).build();
	}
	
	@PermitAll
	@GET
	@Path("validateUsername/{username}")
	public Response singleUserByUsername(@PathParam("username") String username) {
		if(userRepository.findByUsername(username) != null)
			return Response.status(Status.CONFLICT).build();
		return Response.status(Status.OK).build();
	}
	
	@PermitAll
	@GET
	@Path("validateEmail/{email}")
	public Response singleUserByEmail(@PathParam("email") String email) {
		if(userRepository.findByEmail(email) != null)
			return Response.status(Status.CONFLICT).build();
		return Response.status(Status.OK).build();
	}
	
	//signin and set JWT token
	@PermitAll
	@POST
	@Path("signin")
	public Response login(User u){
		User user = userRepository.signin(u);
		if(user != null) {
			String userRole;
			if(user.isSuperuser())
				userRole = "ADMIN";
			else {
				if(u.isStaff())
					userRole = "STAFF";
				else
					userRole = "CUSTOMER";
			}
			String token = issueToken(userRole, user.getUsername());
			NewCookie cookie = new NewCookie(COOKIE_NAME, token, "/", "localhost",
					"Connection au site uniquement", 29*60, false);
			return Response.ok()
					.cookie(cookie)
					.header(AUTHORIZATION_PROPERTY, AUTHENTICATION_NAME + token)
					.build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@PermitAll
	@POST
	public Response addUser(User u) {
		userRepository.createUser(u);
		if(userRepository.errors.isEmpty()) {
			//Succes de l'inscription, on attribue à l'utilisateur role et permission
			UserRoleRepository urr = new UserRoleRepository();
			User newUser = userRepository.findByUsername(u.getUsername());
			Long newUserId = newUser.getId();
			boolean isStaff = newUser.isStaff();
			boolean isSuperuser = newUser.isSuperuser();
			UserRole userRole = new UserRole();
			userRole.setUserId(newUserId);
			if(isSuperuser)
			{
				userRole.setRoleId(1L);
				urr.createUserRole(userRole);
			}
			else {
				if(isStaff)
				{
					userRole.setRoleId(2L);
					urr.createUserRole(userRole);
				}
				else {
					userRole.setRoleId(3L);
					urr.createUserRole(userRole);
				}
			}
			//Welcome email
			sendWelcomeEmail(u.getEmail());
			return Response.status(Status.CREATED).build();
		}
		return Response.status(Status.EXPECTATION_FAILED).build();
	}
	
	@PermitAll
	@POST
	@Path("reset/{email}")
	public void sendRestEmail(@PathParam("email") String email) {
		User u = userRepository.findByEmail(email);
		if(u != null && u.getEmail() != null)
			sendResetPasswordEmail(u);
	}
	
	@PermitAll
	@POST
	@Path("resetpassword")
	public Response resetPasswrd(@QueryParam("resetToken") String resetToken) {
		//On décode le token et recherche son Id
		try {
			Jws<Claims> jwsToken = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(resetToken);
			//On recupère l'email depui le token
			String email = jwsToken.getBody().getId();
			boolean passwordSet = userRepository.setPassword(email, resetToken);
			if(passwordSet)
				return Response.status(Status.OK).build();
		} catch (Exception e) {
			//Impossibilité de décoder le token
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@RolesAllowed({"CUSTOMER", "STAFF", "ADMIN"})
	@PUT
	public Response updateUser(User u) {
		userRepository.update(u);
		if(userRepository.errors.isEmpty())return Response.status(Status.OK).build();
		return Response.status(Status.EXPECTATION_FAILED).build();	}
	
	@RolesAllowed({"CUSTOMER", "STAFF", "ADMIN"})
	@DELETE
	@Path("{id}")
	public Response deleteUser(@PathParam("id") long id) {
		User u = new User();
		u.setId(id);
		userRepository.delete(u);
		if(userRepository.errors.isEmpty())return Response.status(Status.OK).build();
		return Response.status(Status.EXPECTATION_FAILED).build();
	}
	
    private String issueToken(String userRole, String userName) {
        
        String jwtToken = Jwts.builder()
        		.setId(userName)
                .setSubject(userRole)
                .setIssuer("YouSHopPretty")
                .claim("TMH", "L. JE")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 29*60*1000L))
                .signWith(SignatureAlgorithm.HS512,secretKey.getBytes() )
                .compact();
        return jwtToken;
    }
    
    private void sendEmail(String to, String subject, String content) {
        
    	Properties props = new Properties();
    	props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        String FROM = "youshoppretty.thewheel@gmail.com";
        String PASS = "MyStrongP@ssword123";
    	
        Session session = Session.getInstance(props,
                new Authenticator() {
                   protected PasswordAuthentication getPasswordAuthentication() {
                      return new PasswordAuthentication(FROM, PASS);
                   }
       	});
        
        try {
        	Message message = new MimeMessage(session);
     	   	message.setFrom(new InternetAddress(FROM));
     	   	//Sending
     	   	message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
     	   	//message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
     	   	message.setSubject(subject);
     	   	message.setContent(content, "text/html");
     	   	Transport.send(message);
		} catch (Exception e) {
			System.out.println("Failed");
			e.printStackTrace();
		}
    }
    
    private boolean sendWelcomeEmail(String to) {
    	String subject = "Bienvenu(e) sur YouShopPretty, La boutique en ligne qui vous ressemble";
    	String message = "<h1>HTML Message</h1>";
    	sendEmail(to, subject, message);
    	return false;
    }
    
    private void sendResetPasswordEmail(User u) {
    	String resetPasswordToken = Jwts.builder()
        		.setId(u.getEmail())
                .setSubject("Reset password")
                .setIssuer("YouSHopPretty")
                .claim("TMH", "L. JE")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 17*60*1000L))
                .signWith(SignatureAlgorithm.HS512,secretKey.getBytes() )
                .compact();
    	
    	//Modify before deploying
    	String baseUrl = "http://localhost/yspfront/site/account/reset.html?resetToken=" + resetPasswordToken;
    	
    	String subject = "Reinitialisation du mot de passe";
    	String message = "<h3>Mot de passe oublié?</h3><br>"
    			+ "<h4>Bonjour " + u.getUsername() + " !</h4><br><br>"
    			+ " Vous recevez cet email parce que vous avez demandé une reinitialisation"
    			+ " de votre mot de passe YouShopPretty. Veuillez trouver ci-dessous le"
    			+ " lien de reinitialisation.<br>" + baseUrl + "<br><br>Si vous n'êtes pas à"
    			+ " l'origine de ce mailn bien vouloir sécuriser votre compte en changeant"
    			+ " de mot de passe.<br>Nous sommes impatient de vous revoir dans votre "
    			+ " boutique préférée.<br><br> L'équipe YouShopPretty";
    	sendEmail(u.getEmail(), subject, message);
    }
    

}
