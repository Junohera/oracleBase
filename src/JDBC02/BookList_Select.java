package JDBC02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookList_Select {

	public static void main(String[] args) {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "juno";
		String pw = "juno";
		String driver = "oracle.jdbc.driver.OracleDriver";
		Connection con = null;
		PreparedStatement ps = null; // SQL 실행도구
		ResultSet rs = null; // SQL 명령 수행 결과를 저장하는 클래스
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, id, pw);
			String sql = "SELECT * FROM BOOKLIST";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			System.out.println("도서번호\t제목\t출판년도\t입고가격\t출고가격\t등급");
			System.out.println("---------------------------------------------");
			while (rs.next()) {
				String booknum = rs.getString("booknum");
				String subject = rs.getString("subject");
				String grade = rs.getString("grade");
				
				int makeyear = rs.getInt("makeyear");
				int inprice = rs.getInt("inprice");
				int outprice = rs.getInt("outprice");

				System.out.printf("%s\t%s\t%d\t%d\t%d\t%s\n", booknum, subject, makeyear, inprice, outprice, grade);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
