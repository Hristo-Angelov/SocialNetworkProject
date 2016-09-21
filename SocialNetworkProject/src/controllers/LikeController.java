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
@WebServlet("/likes")
public class LikeController extends HttpServlet {
	private static final String UNLIKE = "unlike";
	private static final String LIKE = "like";
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
		int postId = Integer.parseInt(request.getParameter("postId"));
		if (action == null) {
			action = "default";
		}
		if (session.getAttribute("user") == null) {
			action = LOGIN;
		}
		// perform action and set URL to appropriate page
		switch (action) {
		case LOGIN:
			url = "/login.jsp";
			request.setAttribute("message", "You are not logged in.");
			break;
		case LIKE:
			url = "/newsfeed.jsp";
			this.like(session, postId);
			session.removeAttribute("originalPost");
			break;
		case UNLIKE:
			url = "/newsfeed.jsp";
			this.unlike(session, postId);
			session.removeAttribute("originalPost");
			break;
		}
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

	private void unlike(HttpSession session, int postId) {
		User user = (User) session.getAttribute("user");
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PostDAOImpl.getInstance().removeLike(postId, user, connection);
		pool.freeConnection(connection);
	}

	private void like(HttpSession session, int postId) {
		User user = (User) session.getAttribute("user");
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PostDAOImpl.getInstance().addLike(postId, user, connection);
		pool.freeConnection(connection);
	}

}
