package JDBC02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookList_Insert {
	public static void main(String args[]) {
		
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
			
			System.out.println("-----------------------");
			
			Scanner sc = new Scanner(System.in);
			System.out.print("제목: ");
			String subject = sc.nextLine();
			System.out.print("출판년도: ");
			String makeyear = sc.nextLine();
			System.out.print("입고가격: ");
			String inprice = sc.nextLine();
			System.out.print("출고가격: ");
			String outprice = sc.nextLine();
			System.out.print("등급(1:전체, 2:청소년구매가능, 3:성인전용): ");
			String grade = sc.nextLine();
			if (grade.equals("1")) grade = "전체";
			else if (grade.equals("2")) grade = "청소년구매가능";
			else if (grade.equals("3")) grade = "성인전용";
			else grade = "전체";
			
			sql = "INSERT INTO BOOKLIST (BOOKNUM, SUBJECT, MAKEYEAR, INPRICE, OUTPRICE, GRADE) VALUES (BOOKLIST_SEQ.NEXTVAL, ?, ?, ?, ?, ?) ";
			ps = con.prepareStatement(sql);
			ps.setString(1, subject);
			ps.setInt(2, Integer.parseInt(makeyear));
			ps.setInt(3, Integer.parseInt(inprice));
			ps.setInt(4, Integer.parseInt(outprice));
			ps.setString(5, grade);
			
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
