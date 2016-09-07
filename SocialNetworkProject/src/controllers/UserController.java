package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.UserDAO;
import database.UserDAOImpl;
import socialnetwork.main.User;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/home")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "/reglog.jsp";

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
			String usernameMessage = RegistrationValidation.validateUsername(username);
			String emailMessage = RegistrationValidation.validateEmail(email);
			String passwordMessage = RegistrationValidation.validatePassword(password);

			if (usernameMessage == null && emailMessage == null && passwordMessage == null) {
				url = "/thanks.jsp";
				user = new User(username, password, email);
				UserDAO userDao = UserDAOImpl.getInstance();
				userDao.insertUser(user);
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

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
