package socialnetwork.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import exceptions.InvalidInputException;
import socialnetwork.main.Post.Hashtag;

public class DataBase {

	private static final int MAX_NUMBER_OF_TRENDING_HASHTAGS = 10;
	private Map<Hashtag, ArrayList<Post>> hashtags = new HashMap<Hashtag, ArrayList<Post>>();
	private Set<User> users = new HashSet<User>();

	private Connection connection;

	public DataBase() {
		try {
			this.connection = DataBase.getConnection();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void validateHashTags() {
		for (Entry<Hashtag, ArrayList<Post>> key : hashtags.entrySet()) {

			if (key.getValue().isEmpty()) {
				hashtags.remove(key.getKey());
			}
		}
	}

	public Map<Hashtag, ArrayList<Post>> getHashtags() {
		return hashtags;
	}

	public Set<User> getUsers() {
		return users;
	}

	public static Connection getConnection() throws SQLException {
		String sql = "jdbc:mysql://localhost:3306/";
		String database = "mydb";
		String user = "root";
		String pass = "003131";
		return DriverManager.getConnection(sql + database, user, pass);

	}

	public ArrayList<Post> getPosts(String hashTag) {
		return (ArrayList<Post>) Collections.unmodifiableList(hashtags.get(hashTag));
	}

	private void enterIsPrivateStatements() {
		Connection connection;
		try {
			connection = DataBase.getConnection();
			String query = "insert into is_private (number,isPrivate)" + "values('1','1'),('2','2')";
			Statement statement = connection.createStatement();
			int numberOfRows = statement.executeUpdate(query);
			System.out.println("Number of rows inserted " + numberOfRows);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static PreparedStatement generatePreparedStatement(String query) {
		PreparedStatement statement = null;
		try (Connection connection = DataBase.getConnection();) {

			statement = connection.prepareStatement("insert into posts (create_time,text,user_number)values(?,?,?)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return statement;

	}

	public static Statement generateStatement() {
		Statement statement = null;
		try (Connection connection = DataBase.getConnection();) {

			statement = connection.createStatement();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return statement;

	}

	public void updatePublicPosts() {
		// TODO
	}

	public void addUser(User user) throws InvalidInputException {
		if (user != null) {
			users.add(user);

			try (Statement state = connection.createStatement();) {

				int isPrivateNumber = (user.isPrivate() == false) ? 1 : 2;
				PreparedStatement statement = connection.prepareStatement("insert into users"
						+ "(username,email,password,create_time, is_private)" + "values (?,?,?,?,?)");
				statement.setString(1, user.getUsername());
				statement.setString(2, user.getEmail());
				statement.setString(3, user.getPassword());
				statement.setString(4, user.getJoinDate().toString());
				statement.setInt(5, isPrivateNumber);
				statement.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			throw new InvalidInputException("Not valid User");
		}
	}

	public User getUser(User user) throws InvalidInputException {
		if (Validator.isValidObject(user)) {
			return user;
		}
		throw new InvalidInputException("Not existing user!");
	}

	/**
	 * Loops through all hashtags stored in this post and and adds each one to
	 * the database if it is not already on there, in which case it increases
	 * the hashtag's count. Then the post is added to the list of posts
	 * containing the specific hashtag.
	 * 
	 * @param post
	 *            Post whose hashtags are to be stored in the database
	 */
	public void addHashtags(Post post) {
		for (Hashtag hashtag : post.getHashtags()) {
			if (this.hashtags.containsKey(hashtag)) {
				for (Hashtag tag : this.hashtags.keySet()) {
					if (tag.equals(hashtag)) {
						tag.increaseHashtagCount();
						
					}
				}
			} else {
				this.hashtags.put(hashtag, new ArrayList<Post>());
			}
			this.hashtags.get(hashtag).add(post);
		}
	}

	public String getTrendingHashtags() {
		StringBuilder sb = new StringBuilder();
		List<Hashtag> sortedHashtags = new ArrayList<Hashtag>(this.hashtags.keySet());
		sortedHashtags.sort((tag1, tag2) -> tag2.getCount() - tag1.getCount());
		int topTags = 1;
		for (Hashtag hashtag : sortedHashtags) {
			sb.append(hashtag.getName() + " - " + hashtag.getCount() + "\n");
			if (topTags++ >= MAX_NUMBER_OF_TRENDING_HASHTAGS) {
				break;
			}
		}
		return sb.toString();
	}

	public void deleteHashTag(Hashtag hashtag) throws InvalidInputException {
		if (Validator.isValidObject(hashtag)) {
			if (hashtags.containsKey(hashtag)) {
				synchronized (hashtags) {
					hashtags.remove(hashtag);
				}

			}
		} else {
			throw new InvalidInputException("Not valid hashtag!");
		}

	}

}
