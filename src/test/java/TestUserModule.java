import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import database.DBTestConnection;
import database.UserDAOImpl;
import socialnetwork.main.User;

public class TestUserModule {

	
	@Test
	public void testGetFollowers() {
		List<User> followers = UserDAOImpl.getInstance().getFollowers(16, DBTestConnection.getInstance().getConnection());
		assertNotNull(followers);
		System.out.println(followers);
		for (User user : followers) {
			assertNotNull(user);
			assertNotNull(user.getUsername());
		}
	}
}
