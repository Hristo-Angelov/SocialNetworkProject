package controllers;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.UserDB;
import socialnetwork.main.User;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/registration")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// password dimensions
	private static final int MIN_PASSWORD_LENGTH = 8;
	// username dimenstions
	private static final int MIN_USERNAME_LENGTH = 2;
	private static final int MAX_USERNAME_LENGTH = 20;
	// username pattern
	private static final Pattern USERNAME_REGEX = Pattern.compile("^(?=.{1,20}$)[a-zA-Z0-9_]+");
	// email pattern
	private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "/index.jsp";

		// get current action
		String action = request.getParameter("action");
		if (action == null) {
			action = "join"; // default action
		}

		// perform action and set URL to appropriate page
		if (action.equals("join")) {
			url = "/index.jsp"; // the "join" page
		} else if (action.equals("register")) {
			// get parameters from the request
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String email = request.getParameter("email");

			// store data in User object
			User user = new User();
			String usernameMessage = validateUsername(username);
			String emailMessage = validateEmail(email);
			String passwordMessage = validatePassword(password);

			if (usernameMessage == null && emailMessage == null && passwordMessage == null) {
				url = "/thanks.jsp";
				user = new User(usernameMessage, passwordMessage, emailMessage);
				UserDB.insertUser(user);
			} else { 
				user.setUsername(username);
				user.setEmail(email);
			}
			
			user.setEmail(email);
			request.setAttribute("user", user);
			request.setAttribute("usernameMessage", usernameMessage);
			request.setAttribute("emailMessage", emailMessage);
			request.setAttribute("passwordMessage", passwordMessage);
		}
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

	private String validatePassword(String password) {
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

	private String validateEmail(String email) {
		String emailMessage;
		if (email != null && !email.trim().isEmpty()) {
			if (EMAIL_REGEX.matcher(email).matches()) {
				if (UserDB.isEmailAvailable(email)) {
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

	private String validateUsername(String username) {
		String usernameMessage;
		if (username != null && !username.trim().isEmpty()) {
			if (USERNAME_REGEX.matcher(username).matches()) {
				if (UserDB.isUsernameAvailable(username)) {
					usernameMessage = null;
				} else {
					usernameMessage = "This username is already taken!";
				}
			} else {
				usernameMessage = "Your username can only contain letters, numbers and '_'.";
			}
		} else {
			usernameMessage = "Username field is empty.";
		}
		return usernameMessage;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
