package socialnetwork.main;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import exceptions.InvalidArgumentException;

public class User {
	private static final int MIN_LENGTH_FOR_STRING = 4;
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

	public User(String username, String password, String email) throws InvalidArgumentException {
		this.username = User.validateText(username);
		this.password = User.validateText(password);
		this.email = User.validateText(email);
		this.joinDate = joinDate.now();
	}

	public static String validateText(String text) throws InvalidArgumentException {
		if (text != null && text.length() > MIN_LENGTH_FOR_STRING) {
			return text;
		} else {
			throw new InvalidArgumentException("Not valid text");
		}
	}

}
