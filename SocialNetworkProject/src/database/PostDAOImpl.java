package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.Util;

import exceptions.InvalidInputException;

import socialnetwork.main.Hashtag;
import socialnetwork.main.Post;
import socialnetwork.main.PostType;
import socialnetwork.main.Retweet;
import socialnetwork.main.User;

public class PostDAOImpl implements PostDAO {

	private static final Pattern HASHTAG_REGEX = Pattern.compile("(?<!\\w)#(\\w+)");
	private static final int START_COUNT_VALUE = 1;

	private static PostDAO postDao = null;

	public PostDAOImpl() {
	}

	public static synchronized PostDAO getInstance() {
		if (postDao == null) {
			postDao = new PostDAOImpl();
		}
		return postDao;
	}

	@Override
	public void addLike(int postId, User user, Connection connection) {

		String insertLikeSql = "INSERT INTO likes VALUES (?,?)";

		try (PreparedStatement statement = connection.prepareStatement(insertLikeSql);) {
			statement.setInt(1, postId);
			statement.setInt(2, user.getUserId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void removeLike(int postId, User user, Connection connection) {

		String removeLikeSql = "DELETE FROM likes "
						+ "WHERE (post = " + postId
						+ ") AND (user_number = " + user.getUserId()
						+ ");";
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate(removeLikeSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertPost(Post post, Connection connection) {

		PreparedStatement st = null;

		String query = "INSERT INTO posts (user_id,text,original_post_id, post_type ,create_time) "
				+ "VALUES (?,?,?,?,now())";
		try {
			st = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, post.getPoster().getUserId());
			st.setString(2, post.getText());
			if (post.getOriginalPost() == null) {
				st.setString(3, null);
			} else {
				st.setInt(3, post.getOriginalPost().getPostId());
			}
			st.setInt(4, post.getPostType().ordinal());

			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			post.setPostId(rs.getInt(1));
			try {
				this.findHashtags(post, connection);
			} catch (InvalidInputException e1) {

				e1.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			DBUtil.closePreparedStatement(st);
		}

	}

	@Override
	public Post selectPost(int postId, Connection connection) {
		Post post = null;

		int originalPostId = -1;
		int postType = -1;

		String query = "SELECT * FROM posts " + "WHERE post_id = " + postId;

		try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(query);) {

			post = new Post();
			if (rs.next()) {
				postType = rs.getInt("post_type");
				originalPostId = rs.getInt("original_post_id");
				post.setDateWhenPosted(rs.getTimestamp("create_time").toLocalDateTime());
				post.setPostId(postId);
				post.setText(rs.getString("text"));
				post.setPoster(UserDAOImpl.getInstance().selectUser(rs.getInt("user_id"), connection));
				post.setPostType(PostType.fromInt(postType));
				if (postType != 0) {
					post.setOriginalPost(selectPost(originalPostId, connection));
				}
			} else {
				System.out.println("No such post");
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

		}

		return post;
	}

	@Override
	public List<Post> getRetweets(Post post, Connection connection) {
		List<Post> retweets = new ArrayList<Post>();
		String retweetQuery = "SELECT a.* FROM posts p " 
					+ "JOIN posts a ON p.post_id = a.original_post_id "
					+ "WHERE a.post_type = 2 AND a.original_post_id = " + post.getPostId();
		try (PreparedStatement ps = connection.prepareStatement(retweetQuery); ResultSet rs = ps.executeQuery();) {

			while (rs.next()) {
				Post newPost = new Post();
				newPost.setPostId(rs.getInt("post_id"));
				newPost.setText(rs.getString("text"));
				newPost.setDateWhenPosted(rs.getTimestamp("create_time").toLocalDateTime());
				newPost.setPoster(UserDAOImpl.getInstance().selectUser(rs.getInt("user_id"), connection));
				retweets.add(newPost);
			}
			retweets.sort((p2, p1) -> p1.getDateWhenPosted().compareTo(p2.getDateWhenPosted()));
			post.setRetweets(retweets);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return retweets;
	}

	@Override
	public List<Post> getReplies(Post post, Connection connection) {
		List<Post> answers = new ArrayList<Post>();

		String answerQuery = "SELECT a.* FROM posts p " 
				+ "JOIN posts a ON p.post_id = a.original_post_id "
				+ "WHERE a.post_type = 1 "
				+ "AND a.original_post_id = " + post.getPostId();
		ResultSet rs = null;
		try (PreparedStatement ps = connection.prepareStatement(answerQuery)) {
			rs = ps.executeQuery(answerQuery);
			while (rs.next()) {
				Post newPost = new Post();
				newPost.setPostId(rs.getInt("post_id"));
				newPost.setText(rs.getString("text"));
				newPost.setDateWhenPosted(rs.getTimestamp("create_time").toLocalDateTime());
				newPost.setPoster(UserDAOImpl.getInstance().selectUser(rs.getInt("user_id"), connection));
				answers.add(newPost);
			}
			answers.sort((p2, p1) -> p1.getDateWhenPosted().compareTo(p2.getDateWhenPosted()));
			post.setReplies(answers);

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			DBUtil.closeResultSet(rs);
		}
		return answers;
	}

	@Override
	public List<Post> getUserPosts(User user, Connection connection) {

		List<Post> posts = new ArrayList<Post>();

		String query = "SELECT post_id FROM posts WHERE user_id = (?)";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, user.getUserId());

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				int postId = rs.getInt(1);
				Post post = this.selectPost(postId, connection);
				posts.add(post);

			}
			posts.sort((p1, p2) -> p1.compareTo(p2));
			return posts;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void findHashtags(Post post, Connection connection) throws InvalidInputException {

		List<Hashtag> hashtags = new ArrayList<Hashtag>();
		Matcher matcher = HASHTAG_REGEX.matcher(post.getText());

		while (matcher.find()) {
			Hashtag hashtag = new Hashtag(matcher.group(0));
			String sqlSelect = "SELECT * FROM hashtags WHERE hashtag_text LIKE '" + hashtag.getName() + "';";
			ResultSet rs = null;
			try {
				PreparedStatement statement = connection.prepareStatement(sqlSelect);
				rs = statement.executeQuery(sqlSelect);

				if (rs.next()) {
					int count = rs.getInt("count");
					count++;
					String increaseCountSql = "UPDATE  hashtags SET count = (?)  WHERE hashtag_text LIKE (?) ";

					PreparedStatement ps1 = connection.prepareStatement(increaseCountSql);
					ps1.setInt(1, count);
					ps1.setString(2, hashtag.getName());
					ps1.executeUpdate();
				} else {

					PreparedStatement insertStatement = connection.prepareStatement(
							"INSERT INTO hashtags (hashtag_text, count ) values (?,?)",
							Statement.RETURN_GENERATED_KEYS);
					insertStatement.setString(1, matcher.group(0));
					insertStatement.setInt(2, START_COUNT_VALUE);

					insertStatement.executeUpdate();
					hashtags.add(hashtag);

				}

			} catch (SQLException e) {

				e.printStackTrace();
			} finally {
				DBUtil.closeResultSet(rs);
			}

		}

		for (Hashtag hashtag : hashtags) {
			this.mapHashtagsToPost(hashtag, post, connection);
		}

	}

	@Override
	public void mapHashtagsToPost(Hashtag hashtag, Post post, Connection connection) {

		try {

			String sql = "INSERT INTO hashtags_in_posts VALUES (?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, hashtag.getName());
			statement.setInt(2, post.getPostId());
			statement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public List<Post> getNewsfeed(User user, Connection connection) {
		List<Post> newsFeed = new ArrayList<Post>();

		String followersQuery = "SELECT p.post_id FROM users u "
				+ "JOIN followers f ON (f.follower_id = u.user_id) "
				+ "JOIN posts p ON (p.user_id = f.subject_id) "
				+ "WHERE u.user_id =" + user.getUserId();

		try (PreparedStatement ps = connection.prepareStatement(followersQuery); ResultSet rs = ps.executeQuery();) {

			while (rs.next()) {
				int id = rs.getInt("post_id");
				newsFeed.add(this.selectPost(id, connection));
			}
			newsFeed.sort((p1, p2) -> p1.compareTo(p2));
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
		}
		return newsFeed;
	}

	public TreeSet<User> getLikes(Post post, Connection connection) {

		TreeSet<User> likes = new TreeSet<User>();
		String queryLikes = "SELECT u.* FROM posts p " + "join likes l on(p.post_id = l.post)"
				+ "join users u on(l.user_number = u.user_id)" + "where p.post_id = " + post.getPostId();
		try (PreparedStatement likesStatement = connection.prepareStatement(queryLikes);
				ResultSet rs = likesStatement.executeQuery();) {

			while (rs.next()) {
				User user = new User();

				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));

				likes.add(user);
			}
			post.setLikes(likes);

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
		}
		return likes;
	}

	@Override
	public void deletePost(Post post, Connection connection) {

		String deleteFromLikesSql = "DELETE FROM likes where post  =  (?)";

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(deleteFromLikesSql);
			statement.setInt(1, post.getPostId());
			int numberOfRows = statement.executeUpdate();
			System.out.println(numberOfRows);
		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		String sql = "DELETE FROM posts WHERE post_id = (?);";

		try {
			statement = connection.prepareStatement(sql);
			connection.setAutoCommit(false);

			statement.setInt(1, post.getPostId());
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();

			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

}
