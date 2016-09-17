import java.sql.Connection;

import org.junit.Test;

import database.DBTestConnection;
import database.UserDAOImpl;
import socialnetwork.main.User;

public class TestRegistrationModule {

	@Test
	public void TestRegistration() {
		Connection connection = DBTestConnection.getInstance().getConnection();
		UserDAOImpl.getInstance().insertUser(new User("JackSmack", "VoteTrump", "kony2012@gmail.com"), connection);
	}
}
