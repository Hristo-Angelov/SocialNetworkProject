package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.InvalidInputException;
import socialnetwork.main.User;
import socialnetwork.main.Validator;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		} else if (action.equals("add")) {
			// get parameters from the request
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String email = request.getParameter("email");

			// store data in User object
			User user;
			String message;
			try {
				user = new User(username, password, email);
				message = null;
				url = "/thanks.jsp";
			} catch (InvalidInputException e) {
				user = new User();
				message = e.getMessage();
				e.printStackTrace();
			}
			
			request.setAttribute("user", user);
			request.setAttribute("message", message);
		}
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
