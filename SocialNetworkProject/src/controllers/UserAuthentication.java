package controllers;

import database.UserDAO;
import database.UserDAOImpl;
import socialnetwork.main.PasswordUtil;
import socialnetwork.main.User;

public class UserAuthentication {

	public static String validateUser(User user) {
		UserDAO userDao = UserDAOImpl.getInstance();
		String username = user.getUsername();
		if (userDao.usernameExists(username)) {
			if (PasswordUtil.validatePassword(user.getPassword(), userDao.getUserPasswordHash(username))) {
				return null;
			} else {
				return "Wrong password.";
			}
		} else {
			return "No user registered by that name.";
		}
	}



}
