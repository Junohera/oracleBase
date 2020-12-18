package JDBC01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBC_Insert {

	public static void main(String[] args) {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "juno";
		String pw = "juno";
		String driver = "oracle.jdbc.driver.OracleDriver";
		Connection con = null;
		PreparedStatement ps = null; // SQL 실행도구
		
		Scanner sc = new Scanner(System.in);
		System.out.println("저장할 번호를 입력하세요:");
		String num = sc.nextLine();
		System.out.println("이름을 입력하세요:");
		String name = sc.nextLine();
		System.out.println("이메일 입력하세요:");
		String email = sc.nextLine();
		System.out.println("전화번호를 입력하세요:");
		String tel = sc.nextLine();

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, id, pw);
			
			// String sql = "INSERT INTO CUSTOMER VALUES(" + num + ", '" + name + "', '" + email + "', '" + tel + "' )";

			String sql = "INSERT INTO CUSTOMER VALUES(?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(num));
			ps.setString(2, name);
			ps.setString(3, email);
			ps.setString(4, tel);

			int result = ps.executeUpdate();
			if (result == 1) System.out.println("저장 성공~");
			else System.out.println("저장 실패 ㅠ");
			
		} catch (ClassNotFoundException e) {e.printStackTrace();
		} catch (SQLException e) {e.printStackTrace();
		} catch (Exception e) {e.printStackTrace();}
		
		try {
			if (ps != null) ps.close();
			if (con != null) con.close();
		} catch (SQLException e) {e.printStackTrace();}
	}

}
