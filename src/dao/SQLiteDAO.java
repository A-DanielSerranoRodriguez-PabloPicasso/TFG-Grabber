package dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import utils.Utils;

public class SQLiteDAO {

	private Connection conn;

	private static SQLiteDAO dao;

	private SQLiteDAO() {
		File file = new File(Utils.getFolderPath() + "/db.db");
		System.out.println(file.exists());
		if (file.exists()) {
			String url = "jdbc:sqlite://" + file.getAbsolutePath();

			try {
				conn = DriverManager.getConnection(url);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static SQLiteDAO getDao() {
		if (dao == null)
			dao = new SQLiteDAO();

		return dao;
	}

	public Connection getConn() throws SQLException {
		return conn;
	}

	public void closeConn() throws SQLException {
		conn.close();
	}
}
