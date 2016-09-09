package database;

import socialnetwork.main.User;

public interface UserDAO {

	public boolean isUsernameAvailable(String username);

	public boolean isEmailAvailable(String email);

	public User selectUser(String username);
	
	public void insertUser(User user);
	
	public void updateUser(User user);
	
	public void removeUser(User user);
	
	public boolean usernameExists(String username);
	
	public String getUserPasswordHash(String username);
}
