import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import database.DBTestConnection;
import database.UserDAOImpl;
import socialnetwork.main.User;

public class TestUserModule {

	
	@Test
	public void testGetFollowers() {
		Connection connection = DBTestConnection.getInstance().getConnection();
		List<User> followers = UserDAOImpl.getInstance().getFollowers(16, connection);
		assertNotNull(followers);
		System.out.println(followers);
		for (User user : followers) {
			assertNotNull(user);
			assertNotNull(user.getUsername());
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
