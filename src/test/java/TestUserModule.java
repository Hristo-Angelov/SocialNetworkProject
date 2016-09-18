import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import database.DBTestConnection;
import database.UserDAO;
import database.UserDAOImpl;
import socialnetwork.main.User;

public class TestUserModule {

//	
//	@Test
//	public void testGetFollowers() {
//		Connection connection = DBTestConnection.getInstance().getConnection();
//		List<User> followers = UserDAOImpl.getInstance().getFollowers(16, connection);
//		assertNotNull(followers);
//		System.out.println(followers);
//		for (User user : followers) {
//			assertNotNull(user);
//			assertNotNull(user.getUsername());
//		}
//		try {
//			connection.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void testFollow() {
		Connection connection = DBTestConnection.getInstance().getConnection();
		UserDAO uDAO = UserDAOImpl.getInstance();
		User subject = uDAO.selectUser(16, connection);
		User follower = uDAO.selectUser(15, connection);
		assertNotNull(subject);
		assertNotNull(follower);
		uDAO.followUser(subject, follower, connection);
		List<User> followers = uDAO.getFollowers(subject.getUserId(), connection);
		assertNotNull(followers);
		for (User user : followers) {
			assertNotNull(user);
			System.out.println(user.getUsername());
		}
		uDAO.unfollowUser(subject, follower, connection);
		followers = uDAO.getFollowers(subject.getUserId(), connection);
		for (User user : followers) {
			System.out.println(user);
			assertNotEquals(user.getUsername(), follower.getUsername());
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
