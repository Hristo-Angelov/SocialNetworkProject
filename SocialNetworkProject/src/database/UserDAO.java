package database;

import java.sql.Connection;
import java.util.List;

import socialnetwork.main.User;

public interface UserDAO {

	public boolean isUsernameAvailable(String username);

	public boolean isEmailAvailable(String email);

	public User selectUser(String username);
	
	public void insertUser(User user);
	
	public void updateUser(User user);
	
	public void removeUser(User user);
	
	public String getUserPasswordHash(String username);

	public void insertUser(User user, Connection connection);

	public User selectUser(int userID);

	public User selectUser(int int1, Connection connection);

	public String getUserPasswordHash(String username, Connection connection);

	public List<User> getFollowers(int userId);

	public List<User> getFollowers(int userId, Connection connection);
}
