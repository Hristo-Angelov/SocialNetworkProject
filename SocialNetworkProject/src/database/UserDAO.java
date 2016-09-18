package database;

import java.sql.Connection;
import java.util.List;

import socialnetwork.main.User;

public interface UserDAO {

	public void insertUser(User user, Connection connection);

	public User selectUser(int userID, Connection connection);

	public String getUserPasswordHash(String username, Connection connection);

	public List<User> getFollowers(int userId, Connection connection);

	boolean isUsernameAvailable(String username, Connection connection);

	boolean isEmailAvailable(String email, Connection connection);

	User selectUser(String username, Connection connection);
}
