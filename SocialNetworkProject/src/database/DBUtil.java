package database;

import java.sql.*;

public class DBUtil {

	public static void closeStatement(Statement s) {
		try {
			if (s != null) {
				s.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closePreparedStatement(Statement ps) {
		try {
			System.out.println("Chekcing that PS isn't null");
			if (ps != null) {
				System.out.println("closing PS");
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeResultStatement(Statement rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
