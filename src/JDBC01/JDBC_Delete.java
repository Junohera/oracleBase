package JDBC01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBC_Delete {

	public static void main(String[] args) {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "juno";
		String pw = "juno";
		String driver = "oracle.jdbc.driver.OracleDriver";
		Connection con = null;
		PreparedStatement ps = null; // SQL 실행도구
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, id, pw);
			
			Scanner sc = new Scanner(System.in);
			System.out.print("삭제할 회원의 번호를 선택하세요:");
			int num = sc.nextInt();
			String sql = "DELETE FROM CUSTOMER WHERE NUM = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			ps.executeUpdate();
			
		} catch (ClassNotFoundException e) {e.printStackTrace();
		} catch (SQLException e) {e.printStackTrace();
		} catch (Exception e) {e.printStackTrace();}
		
		try {
			if (ps != null) ps.close();
			if (con != null) con.close();
		} catch (SQLException e) {e.printStackTrace();}
	}

}
