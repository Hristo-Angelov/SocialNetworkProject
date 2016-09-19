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
import database.PostDAOImpl;
import socialnetwork.main.Post;
import socialnetwork.main.User;

/**
 * Servlet implementation class LikeController
 */
@WebServlet("/like")
public class LikeController extends HttpServlet {
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
		String action = (String) session.getAttribute("action");
		if (action == null) {
			action = "default";
		}
		if (session.getAttribute("user") == null) {
			action = "login";
		}
		// perform action and set URL to appropriate page
		if (action.equals("login")) {
			url = "/login.jsp"; // the "join" page
			request.setAttribute("message", "You are not logged in.");
		} else {
			if (action.equals("like")) {
				Post originalPost = (Post)session.getAttribute("originalPost");
				url = "/post.jsp?postId=" + originalPost.getPostId();
				this.like(session);
				session.removeAttribute("originalPost");
			} else {
				if (action.equals("unlike")) {
					Post originalPost = (Post)session.getAttribute("originalPost");
					url = "/post.jsp?postId=" + originalPost.getPostId();
					this.unlike(session);
					session.removeAttribute("originalPost");
				}
			}
		}
		session.removeAttribute("action");
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

	private void unlike(HttpSession session) {
		Post post = (Post)session.getAttribute("originalPost");
		User user = (User)session.getAttribute("user");
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PostDAOImpl.getInstance().addLike(post, user, connection);
		pool.freeConnection(connection);
	}

	private void like(HttpSession session) {
		Post post = (Post)session.getAttribute("originalPost");
		User user = (User)session.getAttribute("user");
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PostDAOImpl.getInstance().addLike(post, user, connection);
		pool.freeConnection(connection);
	}

}
