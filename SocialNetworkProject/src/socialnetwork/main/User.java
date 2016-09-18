package socialnetwork.main;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import database.PostDAOImpl;
import exceptions.InvalidInputException;
import interfaces.IUser;

public class User implements IUser, Serializable, Comparable {

	// required fields
	private int userId;
	private String username;
	private String password;
	private String email;
	private final LocalDate joinDate;
	private int idInDatabase;


	// changeable user inforamtion
	private File picture;
	private boolean isPrivate;

	// automatically tracked user data
	private List<Post> likedPosts = new ArrayList<Post>();
	private List<Post> myPosts = new ArrayList<Post>();
	private Set<Post> newsfeed = new TreeSet<Post>();

	private List<User> followers = new ArrayList<User>();
	private Set<User> followedUsers = new HashSet<User>();
	private List<User> followRequests = new ArrayList<User>();

	public User() {
		this.joinDate = LocalDate.now();
		this.username = "";
		this.password = "";
		this.email = "";
	}

	public User(String username, String password, String email) {
		this();
		this.username = username;
		setPassword(password);
		this.email = email;
	}



	public void changePassword(String newPassword) {
		this.password = newPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<User> getFollowRequests() {
		return followRequests;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
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


//	private int getUserDbId() {
//		int userId = 0;
//
//		try (Connection connection = DataBase.getConnection();) {
//
//			Statement statement = connection.createStatement();
//			ResultSet set = statement
//					.executeQuery("select user_number from users where username = '" + this.getUsername() + "'");
//
//			if (set.first()) {
//				userId = set.getInt(1);
//			}
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return userId;
//
//	}

//
//	public void addPost(Post post) throws InvalidInputException {
//		if (Validator.isValidObject(post)) {
//			this.myPosts.add(post);
//			try(Connection con = DataBase.getConnection()) {
//				
//				
//				PreparedStatement statement = con.prepareStatement("insert into posts (idpost,create_time, text,user_number)values(?,?,?,?)");
//	
//				statement.setInt(1, post.getPostId());
//				statement.setString(2, post.getDateWhenPosted().toString());
//				statement.setString(3, post.getText());
//				statement.setInt(4, this.getUserDbId());
//				int rows = statement.executeUpdate();
//				System.out.println("posts entered" + rows);
//				
//
//				// statement = DataBase.generatePreparedStatement(query);
//
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		} else {
//
//			throw new InvalidInputException("Error! Can't post a non-existant post.");
//		}
//	}

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

//	@Override
//	public void deletePost(Post post) throws InvalidInputException {
//		try {
//			if (Validator.isValidObject(post)) {
//				if (this.myPosts.remove(post)) {
//
//					post.delete();
//				}
//			}
//		} catch (InvalidInputException e) {
//			throw new InvalidInputException("This post no longer exists.", e);
//		}
//	}

//	@Override
//	public void reply(Post originalPost, Post myReply) throws InvalidInputException {
//		try {
//			if (Validator.isValidObject(originalPost)) {
//				myReply.reply(originalPost);
//			}
//		} catch (Exception e) {
//			throw new InvalidInputException("Cannot retweet a non-existent post.", e);
//		}
//		addPost(myReply);
//	}

//	@Override
//	public void retweet(Post originalPost, Post myRetweet) throws InvalidInputException {
//		try {
//			if (Validator.isValidObject(originalPost)) {
//				Retweet retweet = myRetweet.retweet(originalPost);
//				addPost(retweet);
//				try(Connection con = DataBase.getConnection()){
//					Statement statement = con.createStatement();
//					statement.executeUpdate("update posts set question_post  = '" + originalPost.getPostId() +"' where idpost = '" + retweet.getPostId()+"'");
//					PreparedStatement prepStatement = con.prepareStatement("insert into retweets (idretweet,text)values(?,?)");
//					prepStatement.setInt(1,retweet.getPostId());
//					prepStatement.setString(2, retweet.getText());
//					prepStatement.executeUpdate();
//				}catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		} catch (Exception e) {
//			throw new InvalidInputException("Cannot retweet a non-existent post.", e);
//		}
//		// addPost(myRetweet);
//
//	}

	

	@Override
	public String toString() {
		return this.username;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public Set<Post> getNewsfeed() {
		return PostDAOImpl.getInstance().getNewsfeed(this);
	}

	public void setNewsfeed(Set<Post> newsfeed) {
		this.newsfeed = newsfeed;
	}

	@Override
	public int compareTo(Object other) {
		return this.username.compareTo(((User)other).getUsername());
	}
	
	
	
}
