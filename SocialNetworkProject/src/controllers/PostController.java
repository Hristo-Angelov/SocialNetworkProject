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
	private static final String REPLY = "reply";
	private static final String RETWEET = "retweet";
	private static final String TWEET = "tweet";
	private static final String LOGIN = "login";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String url = "/newsfeed.jsp";

		HttpSession session = request.getSession();

		// get current action
		String action = request.getParameter("action");
		if (action == null) {
			action = "default";
		}
		if (session.getAttribute("user") == null) {
			action = LOGIN;
		}

		Post originalPost = null;
		// perform action and set URL to appropriate page
		switch (action) {
		case LOGIN:
			url = "/login.jsp";
			request.setAttribute("message", "You are not logged in.");
			break;
		case TWEET:
			url = "/newsfeed.jsp";
			this.addTweet(request, session, PostType.REGULAR);
			break;
		case RETWEET:
			originalPost = (Post) session.getAttribute("originalPost");
			url = "/post.jsp?postId=" + originalPost.getPostId();
			this.addTweet(request, session, PostType.RETWEET, originalPost);
			break;
		case REPLY:
			originalPost = (Post) session.getAttribute("originalPost");
			url = "/post.jsp?postId=" + originalPost.getPostId();
			this.addTweet(request, session, PostType.ANSWER, originalPost);
			break;
		}
		
		session.removeAttribute("originalPost");
		getServletContext().getRequestDispatcher(url).forward(request, response);

	}

	private void addTweet(HttpServletRequest request, HttpSession session, PostType postType) {
		this.addTweet(request, session, postType, null);
	}

	private void addTweet(HttpServletRequest request, HttpSession session, PostType postType, Post originalPost) {
		// get parameters from the request
		String text = request.getParameter(TWEET);
		User user = (User) session.getAttribute("user");
		Post post = new Post();
		post.setText(text);
		post.setPoster(user);
		post.setPostType(postType);
		if (PostValidation.validatePost(post, request.getSession())) {
			PostDAO postDao = PostDAOImpl.getInstance();
			ConnectionPool pool = ConnectionPool.getInstance();
			Connection connection = pool.getConnection();
			post.setOriginalPost(originalPost);
			postDao.insertPost(post, connection);
			pool.freeConnection(connection);
		}
	}

}
