package database;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import com.sun.jmx.snmp.Timestamp;

import socialnetwork.main.Post;
import socialnetwork.main.User;

public class PostDAOImpl implements PostDAO {

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

	@Override
	public void insertPost(Post post) {
		Connection connection = pool.getConnection();
		PreparedStatement st = null;

		String query = "INSERT INTO posts (user_id,text,original_post_id,create_time, post_type) " 
					+ "VALUES (?,?,?,?,now())";
		try {
			st = connection.prepareStatement(query);
			User user = post.getPoster();
			System.out.println("User: " + user.getUserId());
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
		int originalPostId = -1;
		Connection connection = pool.getConnection();
		String query = "SELECT * FROM posts "
				+ "WHERE post_id = " + postId;
		try {
			Statement st = connection.createStatement();
			ResultSet set = st.executeQuery(query);
			while(set.next()){
				
				post = new Post();
				originalPostId = set.getInt("original_post");
				post.setText(set.getString("text"));
				
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return null;
	}

	@Override
	public List<Post> getUserPosts(User user) {
		
		return null;
	}

	@Override
	public List<Post> getNewsfeed(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getLikes(Post post) {
		// TODO Auto-generated method stub
		return null;
	}

}
