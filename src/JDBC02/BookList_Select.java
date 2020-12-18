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
		ResultSet res = null; // SQL 명령 수행 결과를 저장하는 클래스
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, id, pw);
			String sql = "SELECT * FROM BOOKLIST";
			ps = con.prepareStatement(sql);
			res = ps.executeQuery();
			System.out.println("도서번호\t제목\t출판년도\t입고가격\t출고가격\t등급");
			System.out.println("---------------------------------------------");
			while (res.next()) {
				String booknum = res.getString("booknum");
				String subject = res.getString("subject");
				String grade = res.getString("grade");
				
				int makeyear = res.getInt("makeyear");
				int inprice = res.getInt("inprice");
				int outprice = res.getInt("outprice");

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
