package database;

import java.sql.*;

import socialnetwork.main.PasswordUtil;
import socialnetwork.main.User;

public class UserDAOImpl implements UserDAO {

	private static UserDAO userDao = null;
	private static ConnectionPool pool;

	private UserDAOImpl() {
		pool = ConnectionPool.getInstance();
	}

	public static synchronized UserDAO getInstance() {
		if (userDao == null) {
			userDao = new UserDAOImpl();
		}
		return userDao;
	}

	public boolean isUsernameAvailable(String username) {
		Connection connection = pool.getConnection();
		return isUsernameAvailable(username, connection);
	}
	
	public boolean isUsernameAvailable(String username, Connection connection) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT username FROM users "
				+ "WHERE username = ?";
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, username);
			rs = ps.executeQuery();
			return !rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}

	public boolean isEmailAvailable(String email) {
		Connection connection = pool.getConnection();
		return isEmailAvailable(email, connection);
	}
	
	public boolean isEmailAvailable(String email, Connection connection) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT email FROM users "
				+ "WHERE email = ?";
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();
			return !rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}

	public void insertUser(User user) {
		Connection connection = pool.getConnection();
		insertUser(user, connection);
	}

	public void insertUser(User user, Connection connection) {
		PreparedStatement ps = null;
		System.out.println("inserting user: " + user.getUsername());
		String query = "INSERT INTO users (username, email, password, registration_date, is_private) "
				+ "VALUES (?, ?, ?, now(), false)";
		String hashedPass = PasswordUtil.generatePasswordHash(user.getPassword());
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, hashedPass);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}
	
	public void updateUser(User user) {
		// TODO
	}

	public void removeUser(User user) {
		// TODO
	}

	@Override
	public User selectUser(String username) {
		Connection connection = pool.getConnection();
		return selectUser(username, connection);
	}

	public User selectUser(String username, Connection connection) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT * FROM users " 
					+ "WHERE username = ?";

		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, username);
			rs = ps.executeQuery();
			User user = null;
			if (rs.next()) {
				user = new User();
				user.setUserId(Integer.parseInt(rs.getString("user_id")));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setUserId(rs.getInt("user_id"));
				user.setPassword(rs.getString("password"));
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}
	
	@Override
	public User selectUser(int userID) {
		Connection connection = pool.getConnection();
		return selectUser(userID, connection);
	}

	public User selectUser(int userID, Connection connection) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT * FROM users " 
					+ "WHERE user_id = ?";

		try {
			ps = connection.prepareStatement(query);
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			User user = null;
			if (rs.next()) {
				user = new User();
				user.setUserId(Integer.parseInt(rs.getString("user_id")));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setUserId(rs.getInt("user_id"));
				user.setPassword(rs.getString("password"));
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}
	
	@Override
	public String getUserPasswordHash(String username) {
		String pass = selectUser(username).getPassword().trim();
		return pass;
	}
	
	public String getUserPasswordHash(String username, Connection connection) {
		String pass = selectUser(username, connection).getPassword().trim();
		return pass;
	}

}
