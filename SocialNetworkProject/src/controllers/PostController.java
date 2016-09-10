package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.PostDAO;
import database.PostDAOImpl;
import database.UserDAO;
import database.UserDAOImpl;
import socialnetwork.main.Post;
import socialnetwork.main.User;

/**
 * Servlet implementation class PostController
 */
@WebServlet("/welcome")
public class PostController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String url = "/login.jsp";
		
		HttpSession session = request.getSession();

		// get current action
		String action = request.getParameter("action");
		if (action == null) {
			action = "login"; // default action
		}
		
		// perform action and set URL to appropriate page
		if (action.equals("login")) {
			url = "/login.jsp"; // the "join" page
			request.setAttribute("message", "You are not logged in.");
		} else {
			if (action.equals("tweet")) {
				// get parameters from the request
				String text = request.getParameter("tweet");
				User user = (User) session.getAttribute("user");
				Post post = new Post();
				post.setText(text);
				post.setPoster(user);
				if (PostValidation.validatePost(post)) {
					url = "/thanks.jsp";
					PostDAO postDao = PostDAOImpl.getInstance();
					postDao.insertPost(post);
				} else {
					url = "/registration.jsp";
					request.setAttribute("post", post);					
				}
			} 
		}

		getServletContext().getRequestDispatcher(url).forward(request, response);

	}

}
