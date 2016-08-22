package socialnetwork.main;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import exceptions.InvalidInputException;
import interfaces.IUser;

public class User implements IUser {

	// password dimensions
	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final int MAX_PASS_WORD_LENGTH = 32;
	// username dimenstions
	private static final int MIN_USERNAME_LENGTH = 2;
	private static final int MAX_USERNAME_LENGTH = 20;

	// required fields
	private String username;
	private String password;
	private String email;
	private final LocalDate joinDate;
	private DataBase database;

	// changeable user inforamtion
	private File picture;
	private boolean isPrivate;

	// automatically tracked user data
	private final List<Post> likedPosts = new ArrayList<Post>();
	private final List<Post> myPosts = new ArrayList<Post>();

	private final List<User> followers = new ArrayList<User>();
	private final Set<User> followedUsers = new HashSet<User>();
	private final List<User> followRequests = new ArrayList<User>();

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

	public User(String username, String password, String email, DataBase database) throws InvalidInputException {
		this(username, password, email);
		if (Validator.isValidObject(database)) {
			this.database = database;
		}
	}
	// public void LogIn(String username, String password) {
	// TODO
	// }
	//

	public void changePassword(String newPassword) throws InvalidInputException {
		if (Validator.isValidString(newPassword, MIN_PASSWORD_LENGTH, MAX_PASS_WORD_LENGTH)
				&& (!this.password.equals(newPassword))) {
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

	public void likePost(Post post) throws InvalidInputException {
		try {
			if (Validator.isValidObject(post)) {
				this.likedPosts.add(post);
				post.addLike(this);
			}
		} catch (InvalidInputException e) {
			throw new InvalidInputException("Error! Can't like a non-existant post.");
		}
	}

	public List<Post> getMyPosts() {
		return myPosts;
	}

	public void addPost(Post post) throws InvalidInputException {
		try {
			if (Validator.isValidObject(post)) {
				this.myPosts.add(post);
			}
		} catch (InvalidInputException e) {
			throw new InvalidInputException("Error! Can't post a non-existant post.");
		}
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	/**
	 * Sets profile to "private" if passed the argument "true", so unapproved
	 * users cannot see this user's posts. While user's profile is set to
	 * private, all followers need explicit permission to see user's posts. If
	 * passed the argument "false", user's profile is set to "public" and all
	 * their posts are freely visible and any previous follow requests are
	 * approved.
	 * 
	 * @param isPrivate
	 *            Value of user's privacy settings
	 */
	public void setPrivate(boolean isPrivate) {
		if (!isPrivate) {
			for (User user : followRequests) {
				try {
					this.approveFollower(user);
				} catch (InvalidInputException e) {
					e.printStackTrace();
				}
			}
		}
		this.isPrivate = isPrivate;
	}

	public String getUsername() {
		return username;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public Set<User> getFollowedUsers() {
		return followedUsers;
	}

	/**
	 * Send a follow request to a user. If their profile is set to "private" a
	 * request will be sent, otherwise the request is implicitly granted.
	 * 
	 * @param user
	 *            User to be followed pending approval.
	 * @throws InvalidInputException 
	 */
	public void sendFollowRequest(User user) throws InvalidInputException {
		if (user.isPrivate) {
			user.followRequests.add(this);
		} else {
			this.setFollowerPermission(user, true);
		}
	}

	/**
	 * Allows follower to see this user's private posts.
	 * 
	 * @param follower
	 *            User being given permission to see this user's private posts.
	 * @throws InvalidInputException
	 *             If follower does not exist.
	 */
	public void approveFollower(User follower) throws InvalidInputException {
		this.setFollowerPermission(follower, true);
	}

	/**
	 * Denies follower from seeing this user's private posts.
	 * 
	 * @param follower
	 *            User being denied permission to see this user's private posts.
	 * @throws InvalidInputException
	 *             If follower does not exist.
	 */
	public void rejectFollower(User follower) throws InvalidInputException {
		this.setFollowerPermission(follower, false);
	}

	private void setFollowerPermission(User follower, boolean permission) throws InvalidInputException {
		try {
			if (Validator.isValidObject(follower)) {
				if (permission) {
					follower.follow(this);
				}
				this.followRequests.remove(follower);
			}
		} catch (InvalidInputException e) {
			throw new InvalidInputException("Error! No such user.", e);
		}
	}

	private void follow(User follower) {
		follower.followedUsers.add(this);
		this.followers.add(follower);
	}

	/**
	 * Unfollows a previously followed user.
	 * 
	 * @param followed
	 *            User to be unfollowed.
	 */
	public void unfollow(User followed) {
		followed.followers.remove(this);
		this.followedUsers.remove(followed);
	}

	@Override
	public void deletePost(Post post) throws InvalidInputException {
		try {
			if (Validator.isValidObject(post)) {
				if (this.myPosts.remove(post)) {

					post.delete();
				}
			}
		} catch (InvalidInputException e) {
			throw new InvalidInputException("This post no longer exists.", e);
		}
	}

	@Override
	public void reply(Post originalPost, Post myReply) throws InvalidInputException {
		try {
			if (Validator.isValidObject(originalPost)) {
				myReply.reply(originalPost);
			}
		} catch (Exception e) {
			throw new InvalidInputException("Cannot retweet a non-existent post.", e);
		}
		addPost(myReply);
	}

	@Override
	public void retweet(Post originalPost, Post myRetweet) throws InvalidInputException {
		try {
			if (Validator.isValidObject(originalPost)) {
				addPost(myRetweet.retweet(originalPost));
			}
		} catch (Exception e) {
			throw new InvalidInputException("Cannot retweet a non-existent post.", e);
		}
//		addPost(myRetweet);

	}

	public DataBase getDatabase() {
		return this.database;
	}

	@Override
	public String toString() {
		return this.username;
	}
}
