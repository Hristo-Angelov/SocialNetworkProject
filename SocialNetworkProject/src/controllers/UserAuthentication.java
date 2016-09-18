package controllers;

import java.sql.Connection;

import database.ConnectionPool;
import database.UserDAO;
import database.UserDAOImpl;
import socialnetwork.main.PasswordUtil;
import socialnetwork.main.User;

public class UserAuthentication {

	public static String validateUser(User user) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		try {
			UserDAO userDao = UserDAOImpl.getInstance();
			String username = user.getUsername();
			if (!userDao.isUsernameAvailable(username, connection)) {
				if (PasswordUtil.validatePassword(user.getPassword(),
						userDao.getUserPasswordHash(username, connection))) {
					return null;
				} else {
					return "Wrong password.";
				}
			} else {
				return "No user registered by that name.";
			}
		} finally {
			pool.freeConnection(connection);
		}

	}

}
