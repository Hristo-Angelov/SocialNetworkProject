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

import exceptions.InvalidInputException;
import socialnetwork.main.Hashtag;
import socialnetwork.main.Post;
import socialnetwork.main.User;

public class PostDAOImpl implements PostDAO {

	private static final Pattern HASHTAG_REGEX = Pattern.compile("(?<!\\w)#(\\w+)");
	private static final int START_COUNT_VALUE = 1;

	private static PostDAO postDao = null;
	private static ConnectionPool pool;

	public PostDAOImpl() {
		pool = ConnectionPool.getInstance();

	}

	public static synchronized PostDAO getInstance() {
		if (postDao == null) {
			postDao = new PostDAOImpl();
		}
		return postDao;
	}

	public void addLike(Post post, User user) {
		Connection conn = pool.getConnection();
		String insertLikeSql = "INSERT INTO likes VALUES (?,?)";

		try (PreparedStatement statement = conn.prepareStatement(insertLikeSql);) {
			statement.setInt(1, post.getPostId());
			statement.setInt(2, user.getUserId());
			statement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void insertPost(Post post) {
		Connection connection = pool.getConnection();
		PreparedStatement st = null;
		try {
			this.findHashtags(post.getText(), post);
		} catch (InvalidInputException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String query = "INSERT INTO posts (user_id,text,original_post_id, post_type ,create_time) "
				+ "VALUES (?,?,?,?,now())";
		try {
			st = connection.prepareStatement(query);
			User user = post.getPoster();
			// System.out.println("User: " + user.getUserId());
			st.setInt(1, post.getPoster().getUserId());
			st.setString(2, post.getText());
			if (post.getOriginalPost() == null) {
				st.setString(3, null);
			} else {
				st.setInt(3, post.getOriginalPost().getPostId());
			}
			st.setInt(4, post.getPostType().ordinal());

			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			DBUtil.closePreparedStatement(st);
			pool.freeConnection(connection);
		}

	}

	@Override
	public Post selectPost(int postId) {
		Post post = null;
		Set<User> likes = new HashSet<User>();
		Set<Post> answers = new HashSet<Post>();
		Set<Post> retweets = new HashSet<Post>();
		int originalPostId = -1;
		int postType = -1;

		Connection connection = pool.getConnection();

		String query = "SELECT * FROM posts " + "WHERE post_id = " + postId;

		try (Statement st = connection.createStatement(); ResultSet set = st.executeQuery(query);) {

			post = new Post();
			while (set.next()) {
				postType = set.getInt("post_type");
				originalPostId = set.getInt("original_post_id");

				if (postType != 0) {
					PreparedStatement ps = connection
							.prepareStatement("SELECT * FROM posts WHERE original_post_id = " + originalPostId);
					ResultSet rs = ps.executeQuery();
					while (true) {
						Post originalPost = new Post();
						originalPost.setDateWhenPosted(rs.getTimestamp("create_time").toLocalDateTime());
						originalPost.setText(rs.getString("text"));
						// originalPost.setPoster(UserDAOImpl.getInstance().selectUser(rs.getInt("user_id")));

					}
				}

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		String queryLikes = "SELECT * FROM posts p " + "join likes l on(p.post_id = l.post)"
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
			post.setNewLikes(likes);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		String answerQuery = "SELECT a.* FROM posts p" + "JOIN posts a ON p.post_id = a.original_post_id"
				+ "WHERE a.post_type = 1;";
		try (PreparedStatement ps = connection.prepareStatement(answerQuery);
				ResultSet rs = ps.executeQuery(answerQuery);) {

			while (rs.next()) {
				Post newPost = new Post();
				newPost.setText(rs.getString("text"));
				newPost.setDateWhenPosted(rs.getTimestamp("create_time").toLocalDateTime());
				// newPost.setPoster(UserDAOImpl.getInstance().selectUser(rs.getInt("user_id")));
				answers.add(newPost);
			}
			post.setNewReplies(answers);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		String retweetQuery = "SELECT a.* FROM posts p" + "JOIN posts a ON p.post_id = a.original_post_id"
				+ "WHERE a.post_type = 2;";
		try (PreparedStatement ps = connection.prepareStatement(retweetQuery); ResultSet rs = ps.executeQuery();) {

			while (rs.next()) {
				Post newPost = new Post();
				newPost.setText(rs.getString("text"));
				newPost.setDateWhenPosted(rs.getTimestamp("create_time").toLocalDateTime());
				// newPost.setPoster(UserDAOImpl.getInstance().selectUser(rs.getInt("user_id")));
				retweets.add(newPost);
			}
			post.setNewRetweets(retweets);

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			pool.freeConnection(connection);

		}

		return post;
	}

	@Override
	public List<Post> getUserPosts(User user) {
		
		List<Post> posts = new ArrayList<Post>();
		Connection conn = pool.getConnection();
		
		String query = "SELECT post_id FROM posts WHERE user_id = (?)";
		try(PreparedStatement statement = conn.prepareStatement(query);) {
			statement.setInt(1, user.getUserId());
			
			
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
			int postId = 	rs.getInt(1);
			Post post = this.selectPost(postId);
			posts.add(post);
			
			}
			return posts;
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return null;
	}

	private void findHashtags(String text, Post post) throws InvalidInputException {
		Matcher matcher = HASHTAG_REGEX.matcher(text);
		while (matcher.find()) {
			Hashtag hashtag = new Hashtag(matcher.group(0));

			try (Connection conn = pool.getConnection()) {
				PreparedStatement statement = conn.prepareStatement("INSERT INTO hashtags (text, count ) values (?,?)",
						Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, matcher.group(0));
				statement.setInt(2, START_COUNT_VALUE);
				statement.executeUpdate();
				this.mapHashtagsToPost(hashtag, post);
				ResultSet rs = statement.getGeneratedKeys();
				rs.next();
				int hashtagId = rs.getInt(1);
				hashtag.setHashtagId(hashtagId);

				rs.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

	}

	private void mapHashtagsToPost(Hashtag hashtag, Post post) {
		Connection conn = pool.getConnection();
		try {

			String sql = "INSERT INTO hashtags_in_post VALUES (?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, hashtag.getHashtagId());
			statement.setInt(2, post.getPostId());
			statement.executeUpdate();

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public Set<Post> getNewsfeed(User user) {
		Set<Post> newsFeed = new TreeSet<Post>((p1, p2) -> p1.getDateWhenPosted().compareTo(p2.getDateWhenPosted()));

		Connection connection = pool.getConnection();
		String followersQuery = "SELECT p.* FROM users u " + "JOIN followers f ON(u.user_id = f.follower_id)"
				+ "JOIN followers s ON(f.follower_id = s.subject_id)" + "JOIN users m ON(s.subject_id = m.user_id)"
				+ "JOIN posts p ON(m.user_id = p.user_id) WHERE user_id = " + user.getUserId();

		try (PreparedStatement ps = connection.prepareStatement(followersQuery); ResultSet rs = ps.executeQuery();) {

			while (rs.next()) {
				int id = rs.getInt("post_id");
				newsFeed.add(this.selectPost(id));
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			pool.freeConnection(connection);
		}
		return newsFeed;
	}

	@Override
	public TreeSet<User> getLikes(Post post) {

		Connection connection = pool.getConnection();
		TreeSet<User> likes = new TreeSet<User>();
		String queryLikes = "SELECT * FROM posts p " + "join likes l on(p.post_id = l.post)"
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
			post.setNewLikes(likes);

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			pool.freeConnection(connection);
		}
		return likes;
	}

}
