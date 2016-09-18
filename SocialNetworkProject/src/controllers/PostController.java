package controllers;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.ConnectionPool;
import database.PostDAO;
import database.PostDAOImpl;
import socialnetwork.main.Post;
import socialnetwork.main.PostType;
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
		String url = "/newsfeed.jsp";
		
		HttpSession session = request.getSession();

		// get current action
		String action = request.getParameter("action");
		if (action == null && session.getAttribute("user") == null) {
			action = "login"; // default action
		} else {
			action = "tweet";
		}
		
		// perform action and set URL to appropriate page
		if (action.equals("login")) {
			url = "/login.jsp"; // the "join" page
			request.setAttribute("message", "You are not logged in.");
		} else {
			url = "/newsfeed.jsp";
			if (action.equals("tweet")) {
				this.addTweet(request, session, PostType.REGULAR);
			} else {
				if (action.equals("retweet")) {
					this.addTweet(request, session, PostType.RETWEET);
				} else {
					if (action.equals("reply")) {
						this.addTweet(request, session, PostType.ANSWER);
					}
				}
			}
		}

		getServletContext().getRequestDispatcher(url).forward(request, response);

	}

	private void addTweet(HttpServletRequest request, HttpSession session, PostType postType) {
		// get parameters from the request
		String text = request.getParameter("tweet");
		User user = (User) session.getAttribute("user");
		Post post = new Post();
		post.setText(text);
		post.setPoster(user);
		post.setPostType(postType);
		if (PostValidation.validatePost(post, request.getSession())) {
			PostDAO postDao = PostDAOImpl.getInstance();
			ConnectionPool pool = ConnectionPool.getInstance();
			Connection connection = pool.getConnection();
			postDao.insertPost(post, connection);
			pool.freeConnection(connection);
		}
	}

}
