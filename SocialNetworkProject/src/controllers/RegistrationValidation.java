package controllers;

import java.util.regex.Pattern;

import database.UserDAOImpl;

public class RegistrationValidation {

	// password dimensions
	private static final int MIN_PASSWORD_LENGTH = 8;
	// username dimenstions
	private static final int MIN_USERNAME_LENGTH = 2;
	private static final int MAX_USERNAME_LENGTH = 20;
	// username pattern
	private static final Pattern USERNAME_REGEX = Pattern.compile("[a-zA-Z0-9_]+");
	// email pattern
	private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	protected static String validatePassword(String password) {
		String passwordMessage;
		if (password != null && !password.isEmpty()) {
			if (password.length() < MIN_PASSWORD_LENGTH) {
				passwordMessage = "Password cannot be shorter than " + MIN_PASSWORD_LENGTH + " characters long.";
			} else {
				passwordMessage = null;
			}
		} else {
			passwordMessage = "Password field is empty.";
		}
		return passwordMessage;
	}

	protected static String validateEmail(String email) {
		String emailMessage;
		if (email != null && !email.trim().isEmpty()) {
			if (EMAIL_REGEX.matcher(email).matches()) {
				if (UserDAOImpl.getInstance().isEmailAvailable(email)) {
					emailMessage = null;
				} else {
					emailMessage = "This email is already taken!";
				}
			} else {
				emailMessage = "Please enter a valid e-mail.";
			}
		} else {
			emailMessage = "E-mail field is empty.";
		}
		return emailMessage;
	}

	protected static String validateUsername(String username) {
		String usernameMessage;
		if (username != null && !username.trim().isEmpty()) {
			if (username.length() >= MIN_USERNAME_LENGTH) {
				if (username.length() <= MAX_USERNAME_LENGTH) {
					if (USERNAME_REGEX.matcher(username).matches()) {
						if (UserDAOImpl.getInstance().isUsernameAvailable(username)) {
							usernameMessage = null;
						} else {
							usernameMessage = "This username is already taken!";
						}
					} else {
						usernameMessage = "Your username can only contain letters, numbers and '_'.";
					}
				} else {
					usernameMessage = "Username cannot be longerer than " + MAX_USERNAME_LENGTH + " characters long.";
				}
			} else {
				usernameMessage = "Username cannot be shorter than " + MIN_USERNAME_LENGTH + " characters long.";
			}
		} else {
			usernameMessage = "Username field is empty.";
		}
		return usernameMessage;
	}
}
