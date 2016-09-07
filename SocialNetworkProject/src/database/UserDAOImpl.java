package database;

import socialnetwork.main.User;

public class UserDAOImpl implements UserDAO {

	private static UserDAO userDao = null;
	private static ConnectionPool pool;

	private UserDAOImpl() {
		pool = ConnectionPool.getInstance();
	}

	public static synchronized UserDAO getInstance() {
		if (userDao == null) {
			userDao = new UserDAOImpl();
		}
		return userDao;
	}

	public boolean isUsernameAvailable(String username) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isEmailAvailable(String email) {
		// TODO Auto-generated method stub
		return true;
	}

	public void insertUser(User user) {
		// TODO Auto-generated method stub
	}

	public void updateUser(User user) {
		// TODO
	}

	public void removeUser(User user) {
		// TODO
	}

}
