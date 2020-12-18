package JDBC01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// 자바에서 지원하는 데이터베이스 연결 클래스
public class JDBC_Connect {

	public static void main(String[] args) {
		Connection con = null;
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "juno";
		String pw = "juno";
		String driver = "oracle.jdbc.driver.OracleDriver";
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, id, pw);
			System.out.println("데이터베이스 연결 성공 ~!!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DATABASE CONNECTION FAIL");
		} catch (ClassNotFoundException e) {
			System.out.println("DRIVER CLASS NOT FOUND");
		} catch (Exception e) {
			System.out.println("기타의 사유로 연결실패");
		}
		
		try {
			if (con != null) con.close();
			System.out.println("데이터베이스 종료~!!");
		} catch (SQLException e) {
			
		}
	}

}
