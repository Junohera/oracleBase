package JDBC04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class In_OutDao {
	private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String id = "juno";
	private static final String pw = "juno";
	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, id, pw);	
		} catch (ClassNotFoundException e) {e.printStackTrace();
		} catch (SQLException e) {e.printStackTrace();
		} catch (Exception e) {e.printStackTrace();}
		
		return con;
	}
	
	public static void close() {
		try {
			if (con != null) con.close();
			if (ps != null) ps.close();
			if (rs != null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int insert(In_OutDto dto) {
		int result = 0;
		
		con = getConnection();
		String sql = "INSERT INTO IN_OUT ";
		sql += "(INDEXK, DISCOUNT, BOOKNUM, PERSONNUM, OUT_DATE) VALUES ";
		sql += "(IN_OUT_SEQ.NEXTVAL, ?, ?, ?, SYSDATE) ";
		
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, dto.getDiscount());
			ps.setString(2, dto.getBooknum());
			ps.setString(3, dto.getPersonnum());

			result = ps.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();
		} finally {close();}
		
		return result;
	}
	
	public ArrayList<In_OutDto> select() {
		ArrayList<In_OutDto> list = new ArrayList<In_OutDto>();
		con = getConnection();
		String sql = "SELECT * FROM IN_OUT ORDER BY INDEXK";
		
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				In_OutDto inout = new In_OutDto();
				inout.setIndexk(rs.getInt("indexk"));
				inout.setDiscount(rs.getInt("discount"));
				inout.setBooknum(rs.getString("booknum"));
				inout.setPersonnum(rs.getString("personnum"));
				inout.setOut_date(rs.getDate("out_date"));
				list.add(inout);
			}
		} catch (SQLException e) {e.printStackTrace();
		} finally {close();}
		
		return list;
	}
}
