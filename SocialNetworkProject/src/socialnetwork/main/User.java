package socialnetwork.main;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import exceptions.InvalidInputException;

public class User {
	
	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final int MAX_PASS_WORD_LENGTH = 32;
	// private constants
	private static final int MIN_USERNAME_LENGTH = 2;
	private static final int MAX_USERNAME_LENGTH = 20;
	
	// private fields
	private String username;
	private String password;
	private String email;
	private File picture;
	private final LocalDate joinDate;
	private List<Post> likedPosts = new ArrayList<Post>();
	private List<Post> myPosts = new ArrayList<Post>();
	private boolean isPrivate;
	
	private final Set<User> followers = new LinkedHashSet<User>();
	private final Set<User> followed = new LinkedHashSet<User>();

	public User(String username, String password, String email) throws InvalidInputException {
		if (Validator.isValidString(username, MIN_USERNAME_LENGTH, MAX_USERNAME_LENGTH)) {
			this.username = username;
		}
		if (Validator.isValidString(password, MIN_PASSWORD_LENGTH, MAX_PASS_WORD_LENGTH)) {
			this.password = password;
		}
		if (Validator.isValidEmail(email)) {
			this.email = email;
		}
		this.joinDate = LocalDate.now();
	}

//	public void LogIn(String username, String password) {
//		
//	}
//	

	public void changePassword(String newPassword) throws InvalidInputException {
		if (Validator.isValidString(newPassword, MIN_PASSWORD_LENGTH, MAX_PASS_WORD_LENGTH) && (!this.password.equals(newPassword))) {
			this.password = newPassword;
		}
	}

	public String getEmail() {
		return email;
	}

	public void changeEmail(String newEmail) throws InvalidInputException {
		if (Validator.isValidEmail(newEmail) && (!this.password.equals(newEmail))) {
			this.email = newEmail;
		}
	}

	public File getPicture() {
		return picture;
	}

	public void setPicture(File picture) {
		this.picture = picture;
	}

	public List<Post> getLikedPosts() {
		return likedPosts;
	}

	public void setLikedPosts(List<Post> likedPosts) {
		this.likedPosts = likedPosts;
	}

	public List<Post> getMyPosts() {
		return myPosts;
	}

	public void setMyPosts(List<Post> myPosts) {
		this.myPosts = myPosts;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public String getUsername() {
		return username;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public Set<User> getFollowed() {
		return followed;
	}

	
	


}
