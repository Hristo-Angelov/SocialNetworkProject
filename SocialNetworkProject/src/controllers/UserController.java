package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.ConnectionPool;
import database.DBUtil;
import database.UserDAO;
import database.UserDAOImpl;
import socialnetwork.main.User;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/home")
public class UserController extends HttpServlet {
	private static final String UNFOLLOW = "unfollow";
	private static final String FOLLOW = "follow";
	private static final String LOGOUT = "logout";
	private static final String LOGIN = "login";
	private static final String REGISTER = "register";
	private static final String HOME = "home";
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
			action = HOME; // default action
		}

		// perform action and set URL to appropriate page
		switch (action) {
		case HOME:
			url = "/reglog.jsp";
			break;

		case REGISTER:
			url = registerUser(request, session);
			break;
		case LOGIN:
			url = loginUser(request, session);
			break;
		case LOGOUT:
			url = "/reglog.jsp";
			session.invalidate();
			break;
		case FOLLOW:
			url = followUser(request, session);
			break;
		case UNFOLLOW:
			url = unfollowUser(request, session);
			break;
		}

		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

	private String unfollowUser(HttpServletRequest request, HttpSession session) {
		User follower = (User) session.getAttribute("user");

		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		String subjectName = request.getParameter("subjectName");
		User subject = UserDAOImpl.getInstance().selectUser(subjectName, connection);

		UserDAOImpl.getInstance().unfollowUser(subject, follower, connection);
		pool.freeConnection(connection);
		String url = "/profile.jsp?username=" + subject.getUsername();
		return url;
	}

	private String followUser(HttpServletRequest request, HttpSession session) {
		User follower = (User) session.getAttribute("user");

		ConnectionPool pool = ConnectionPool.getInstance();

		Connection connection = pool.getConnection();
		String subjectName = request.getParameter("subjectName");
		User subject = UserDAOImpl.getInstance().selectUser(subjectName, connection);

		UserDAOImpl.getInstance().followUser(subject, follower, connection);
		pool.freeConnection(connection);
		String url = "/profile.jsp?username=" + subject.getUsername();
		return url;
	}

	private String loginUser(HttpServletRequest request, HttpSession session) {
		String url;
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		String message = UserAuthentication.validateUser(user);

		if (message == null) {
			url = "/newsfeed.jsp";
			user = setUserToSession(session, user);
		} else {
			url = "/login.jsp";
			request.setAttribute("message", message);
			request.setAttribute("user", user);
		}
		return url;
	}

	private String registerUser(HttpServletRequest request, HttpSession session) {
		String url;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		User user = new User(username, password, email);
		String usernameMessage = RegistrationValidation.validateUsername(username);
		String emailMessage = RegistrationValidation.validateEmail(email);
		String passwordMessage = RegistrationValidation.validatePassword(password);
		if (usernameMessage == null && emailMessage == null && passwordMessage == null) {
			url = "/newsfeed.jsp";
			UserDAO userDao = UserDAOImpl.getInstance();
			ConnectionPool pool = ConnectionPool.getInstance();
			Connection connection = pool.getConnection();
			userDao.insertUser(user, connection);
			pool.freeConnection(connection);
			user = setUserToSession(session, user);
		} else {
			url = "/registration.jsp";
			request.setAttribute("usernameMessage", usernameMessage);
			request.setAttribute("emailMessage", emailMessage);
			request.setAttribute("passwordMessage", passwordMessage);
			request.setAttribute("user", user);
		}
		return url;
	}

	private User setUserToSession(HttpSession session, User user) {
		UserDAO userDao = UserDAOImpl.getInstance();
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		user = userDao.selectUser(user.getUsername(), connection);
		pool.freeConnection(connection);
		session.setAttribute("user", user);
		return user;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
