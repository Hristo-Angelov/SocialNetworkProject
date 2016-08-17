package socialnetwork.main;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import exceptions.InvalidInputException;

public class User {
	private static final int MIN_USERNAME_LENGTH = 4;
	private static final int MAX_USERNAME_LENGTH = 20;
	private String username;
	private String password;
	private String email;
	private File picture;
	private LocalDate joinDate;
	private List<Post> likedPosts = new ArrayList<Post>();
	private List<Post> myPosts = new ArrayList<Post>();
	
	/**
	 * dali "isPrivate" da go slojim v konstruktura?
	 */
	private boolean isPrivate;
	
	private Set<User> followers = new LinkedHashSet<User>();
	private Set<User> followed = new LinkedHashSet<User>();

	public User(String username, String password, String email) throws InvalidInputException {
		this.username = Validator.validateString(username, MIN_USERNAME_LENGTH, MAX_USERNAME_LENGTH);
		this.password = Validator.validateString(password);
		this.email = Validator.validateString(email);
		this.joinDate = joinDate.now();
	}

	
	


}
