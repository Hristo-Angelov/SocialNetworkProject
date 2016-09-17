package controllers;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.PostDAOImpl;
import database.UserDAO;
import database.UserDAOImpl;
import exceptions.InvalidInputException;
import socialnetwork.main.Post;
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
		
		request.setCharacterEncoding("UTF-8");
		String url = "/reglog.jsp";
		
		HttpSession session = request.getSession();

		// get current action
		String action = request.getParameter("action");
		if (action == null) {
			action = "home"; // default action
		}

		// perform action and set URL to appropriate page
		if (action.equals("home")) {
			url = "/reglog.jsp"; // the "join" page
		} else {
			if (action.equals("register")) {
				// get parameters from the request
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				String email = request.getParameter("email");

				// store data in User object
				User user = new User(username, password, email);
				String usernameMessage = RegistrationValidation.validateUsername(username);
				String emailMessage = RegistrationValidation.validateEmail(email);
				String passwordMessage = RegistrationValidation.validatePassword(password);

				if (usernameMessage == null && emailMessage == null && passwordMessage == null) {
					url = "/newsfeed.jsp";
					UserDAO userDao = UserDAOImpl.getInstance();
					userDao.insertUser(user);
					user = setUserToSession(session, user);
				} else {
					url = "/registration.jsp";
					request.setAttribute("usernameMessage", usernameMessage);
					request.setAttribute("emailMessage", emailMessage);
					request.setAttribute("passwordMessage", passwordMessage);
					request.setAttribute("user", user);					
				}
			} else {
				if (action.equals("login")) {
					// get parameters from the request
					String username = request.getParameter("username");
					String password = request.getParameter("password");

					// store data in User object
					User user = new User();
					user.setUsername(username);
					user.setPassword(password);
					String message = UserAuthentication.validateUser(user);

					if (message == null) {
						url = "/newsfeed.jsp";
						user = setUserToSession(session, user);
//						Set<Post> feed = PostDAOImpl.getInstance().getNewsfeed(user);
						Set<Post> feed = new TreeSet<Post>();
						try {
							feed.add(new Post("hi", user));
						} catch (InvalidInputException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						request.setAttribute("feed", feed);
					} else {
						url = "/login.jsp";
						request.setAttribute("message", message);
						request.setAttribute("user", user);
					}
				}
			}
		}

		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

	private User setUserToSession(HttpSession session, User user) {
		UserDAO userDao = UserDAOImpl.getInstance();
		user = userDao.selectUser(user.getUsername());
		session.setAttribute("user", user);
		return user;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
