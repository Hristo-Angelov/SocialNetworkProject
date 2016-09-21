import java.sql.Connection;
import java.util.Random;

import org.junit.Test;

import database.DBTestConnection;
import database.UserDAOImpl;
import socialnetwork.main.User;

public class TestRegistrationModule {

	@Test
	public void TestRegistration() {
		Connection connection = DBTestConnection.getInstance().getConnection();
		UserDAOImpl.getInstance().insertUser(new User("JackSmack" + new Random().nextInt(), "VoteTrump", "kony2012@gmail.com" + new Random().nextInt()), connection);
	}
}
