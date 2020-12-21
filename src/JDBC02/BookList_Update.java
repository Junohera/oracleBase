package JDBC02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookList_Update {
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
			
			select(con, ps, rs, null);
			
			Scanner sc = new Scanner(System.in);
			System.out.print("수정할 도서의 도서번호를 선택하세요:");
			String num = sc.nextLine();
			System.out.print("수정할 항목을 선택하세요. 1. 제목, 2. 출판년도, 3.출고가격");
			String input = sc.nextLine();
			String sql = "";
			switch(input) {
			case "1": 
				sql = "UPDATE BOOKLIST SET SUBJECT = ? WHERE BOOKNUM = ?"; 
				System.out.print("수정할 제목을 입력하세요: ");
				break;
			case "2": 
				sql = "UPDATE BOOKLIST SET MAKEYEAR = ? WHERE BOOKNUM = ?"; 
				System.out.print("수정할 출판년도를 입력하세요: ");
				break;
			case "3": 
				sql = "UPDATE BOOKLIST SET OUTPRICE = ? WHERE BOOKNUM = ?"; 
				System.out.print("수정할 출고가격을 입력하세요: ");
				break;
			}
			
			String val = sc.nextLine();
			ps = con.prepareStatement(sql);
			ps.setString(1, val);
			ps.setString(2, num);
			
			int result = ps.executeUpdate();
			if (result == 1) System.out.println("수정 성공 :)");
			else System.out.println("수정 실패 :(");

			System.out.println();
			System.out.println("수정 결과---------------");

			select(con, ps, rs, num);
			
		} catch (ClassNotFoundException e) {e.printStackTrace();
		} catch (SQLException e) {e.printStackTrace();
		} catch (Exception e) {e.printStackTrace();}
		
		try {
			if (ps != null) ps.close();
			if (con != null) con.close();
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	public static void select(Connection con, PreparedStatement ps, ResultSet rs, String watchNum) {
		String sql = "SELECT * FROM BOOKLIST";
		
		try {
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
				
				if (null != watchNum && booknum.equals(watchNum)) {
					System.out.printf("[변경]: %s\t%s\t%d\t%d\t%d\t%s\n", booknum, subject, makeyear, inprice, outprice, grade);
				} else {
					System.out.printf("%s\t%s\t%d\t%d\t%d\t%s\n", booknum, subject, makeyear, inprice, outprice, grade);
				}

				
			}
			
			System.out.println("-----------------------");
		} catch (SQLException e) {e.printStackTrace();
		} catch (Exception e) {e.printStackTrace();}
	}
}
