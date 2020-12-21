package JDBC03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDao {
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String id = "juno";
	private String pw = "juno";
	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	public int delete(BookDto book) {
		int result = 0;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, id, pw);
			
			String sql = "DELETE FROM BOOKLIST WHERE BOOKNUM = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, book.getBooknum());
			result = ps.executeUpdate();
			
		} catch (ClassNotFoundException e) {e.printStackTrace();
		} catch (SQLException e) {e.printStackTrace();
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {
				if (con != null) con.close();
				if (ps != null) ps.close();
				if (rs != null) rs.close(); // update시에는 미사용하지만 있으나마나 ... just stay
			} catch (SQLException e) {e.printStackTrace();}
		}
		
		return result;
	}

	public int update(BookDto book) {
		int result = 0;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, id, pw);
			
			String sql = "UPDATE BOOKLIST SET ";

			boolean first = true; // 처음여부

			if (!book.getSubject().equals("")) { // 수정할 값이 있으면(문자열)
				if (first) {sql += "SUBJECT = ?"; first = false;} // 처음이면 1: ,없이 2: 처음여부값변경
				else sql += ", SUBJECT = ?"; // 처음아니면 ,포함
			}
			if (book.getMakeyear() != 0) { // 수정할 값이 있으면(정수형)
				if (first) {sql += "MAKEYEAR = ?"; first = false;} // 처음이면 1: ,없이 2: 처음여부값변경
				else sql += ", MAKEYEAR = ?"; // 처음아니면 ,포함
			}
			if (book.getInprice() != 0) { // 수정할 값이 있으면(정수형)
				if (first) {sql += "INPRICE = ?"; first = false;} // 처음이면 1: ,없이 2: 처음여부값변경
				else sql += ", INPRICE = ?"; // 처음아니면 ,포함
			} 
			if (book.getOutprice() != 0) { // 수정할 값이 있으면(정수형)
				if (first) {sql += "OUTPRICE = ?"; first = false;} // 처음이면 1: ,없이 2: 처음여부값변경
				else sql += ", OUTPRICE = ?"; // 처음아니면 ,포함
			} 
			if (!book.getGrade().equals("")) { // 수정할 값이 있으면(문자열)
				if (first) {sql += "GRADE = ?"; first = false;} // 처음이면 1: ,없이 2: 처음여부값변경
				else sql += ", GRADE = ?"; // 처음아니면 ,포함
			} 

			sql += " WHERE BOOKNUM = ?";
			
			ps = con.prepareStatement(sql);

			int cnt = 1;
			first = true;
			if (!book.getSubject().equals("")) { // 수정할 값이 있으면(문자열)
				if (first) {ps.setString(1, book.getSubject()); first = false;} // 처음이면 첫번째자리 변경하고 처음여부 변경
				else ps.setString(++cnt, book.getSubject()); // 처음아니면 ++자리수기억변수에 따른 변경
			}
			if (book.getMakeyear() != 0) { // 수정할 값이 있으면(정수형)
				if (first) {ps.setInt(1, book.getMakeyear()); first = false;}  // 처음이면 첫번째자리 변경하고 처음여부 변경
				else ps.setInt(++cnt, book.getMakeyear()); // 처음아니면 ++자리수기억변수에 따른 변경
			}
			if (book.getInprice() != 0) { // 수정할 값이 있으면(정수형)
				if (first) {ps.setInt(1, book.getInprice()); first = false;}  // 처음이면 첫번째자리 변경하고 처음여부 변경
				else ps.setInt(++cnt, book.getInprice()); // 처음아니면 ++자리수기억변수에 따른 변경
			}
			if (book.getOutprice() != 0) { // 수정할 값이 있으면(정수형)
				if (first) {ps.setInt(1, book.getOutprice()); first = false;}  // 처음이면 첫번째자리 변경하고 처음여부 변경
				else ps.setInt(++cnt, book.getOutprice()); // 처음아니면 ++자리수기억변수에 따른 변경
			}
			if (!book.getGrade().equals("")) { // 수정할 값이 있으면(문자열)
				if (first) {ps.setString(1, book.getGrade()); first = false;}  // 처음이면 첫번째자리 변경하고 처음여부 변경
				else ps.setString(++cnt, book.getGrade()); // 처음아니면 ++자리수기억변수에 따른 변경
			}
			
			ps.setString(++cnt, book.getBooknum());
			
			result = ps.executeUpdate(); // 쿼리실행
		} catch (ClassNotFoundException e) {e.printStackTrace();
		} catch (SQLException e) {e.printStackTrace();
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {
				if (con != null) con.close();
				if (ps != null) ps.close();
				if (rs != null) rs.close(); // update시에는 미사용하지만 있으나마나 ... just stay
			} catch (SQLException e) {e.printStackTrace();}
		}
		
		return result;
	}
	
	public int insert(BookDto book) {
		int result = 0;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, id, pw);

			String sql = "INSERT INTO BOOKLIST (BOOKNUM, SUBJECT, MAKEYEAR, INPRICE, OUTPRICE, GRADE) VALUES (BOOKLIST_SEQ.NEXTVAL, ?, ?, ?, ?, ?) ";
			ps = con.prepareStatement(sql);
			ps.setString(1, book.getSubject());
			ps.setInt(2, book.getMakeyear());
			ps.setInt(3, book.getInprice());
			ps.setInt(4, book.getOutprice());
			ps.setString(5, book.getGrade());

			result = ps.executeUpdate();
		} catch (ClassNotFoundException e) {e.printStackTrace();
		} catch (SQLException e) {e.printStackTrace();
		} catch (Exception e) {e.printStackTrace();
		System.out.println(e.getCause());
		System.out.println(e.getMessage());
		} finally {
			try {
				if (con != null) con.close();
				if (ps != null) ps.close();
				if (rs != null) rs.close(); // update시에는 미사용하지만 있으나마나 ... just stay
			} catch (SQLException e) {e.printStackTrace();}
		}
		
		return result;
	}
	
	public ArrayList<BookDto> select() {
		ArrayList<BookDto> list = new ArrayList<BookDto>();
		// 데이터베이스 테이블의 조회가 여기서 이루어집니다.
		// 결과는 list변수에 담기고, 리턴됩니다.
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, id, pw);

			String sql = "SELECT * FROM BOOKLIST";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				BookDto bdto = new BookDto();
				bdto.setBooknum(rs.getString("booknum"));
				bdto.setSubject(rs.getString("subject"));
				bdto.setMakeyear(rs.getInt("makeyear"));
				bdto.setInprice(rs.getInt("inprice"));
				bdto.setOutprice(rs.getInt("outprice"));
				bdto.setGrade(rs.getString("grade"));
				
				list.add(bdto);
			}
		} catch (ClassNotFoundException e) {e.printStackTrace();
		} catch (SQLException e) {e.printStackTrace();
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {
				if (con != null) con.close();
				if (ps != null) ps.close();
				if (rs != null) rs.close();
			} catch (SQLException e) {e.printStackTrace();}
		}
		
		return list;
	}
	
	public BookDto find(String booknum) {
		BookDto book = new BookDto();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, id, pw);

			String sql = "SELECT * FROM BOOKLIST WHERE BOOKNUM = ?";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, booknum);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				book.setBooknum(rs.getString("booknum"));
				book.setSubject(rs.getString("subject"));
				book.setMakeyear(rs.getInt("makeyear"));
				book.setInprice(rs.getInt("inprice"));
				book.setOutprice(rs.getInt("outprice"));
				book.setGrade(rs.getString("grade"));
			}
			
		} catch (ClassNotFoundException e) {e.printStackTrace();
		} catch (SQLException e) {e.printStackTrace();
		} catch (Exception e) {e.printStackTrace();
		} finally {
			try {
				if (con != null) con.close();
				if (ps != null) ps.close();
				if (rs != null) rs.close();
			} catch (SQLException e) {e.printStackTrace();}
		}
		
		return book;
	}
	
}
