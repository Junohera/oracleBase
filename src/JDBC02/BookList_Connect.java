package JDBC02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookList_Connect {
	public static void main(String args[]) {
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String driver = "oracle.jdbc.driver.OracleDriver";
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, "juno", "juno");
		} catch (ClassNotFoundException e) {e.printStackTrace();
		} catch (SQLException e) {e.printStackTrace();
		} catch (Exception e) {e.printStackTrace();}
		
		try {
			if (ps != null) ps.close();
			if (con != null) con.close();
		} catch (SQLException e) {e.printStackTrace();}
	}
}
