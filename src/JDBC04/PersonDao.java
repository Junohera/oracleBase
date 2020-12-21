package JDBC04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonDao {
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
	
	public ArrayList<PersonDto> select() {
		ArrayList<PersonDto> list = new ArrayList<PersonDto>();
		con = getConnection();
		String sql = "SELECT * FROM PERSON ORDER BY PERSONNUM";
		
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				PersonDto dto = new PersonDto();
				dto.setPersonnum(rs.getString("personnum"));
				dto.setPersonname(rs.getString("personname"));
				dto.setPhone(rs.getString("phone"));
				dto.setBirth(rs.getDate("birth"));
				dto.setEnterdate(rs.getDate("enterdate"));
				dto.setBpoint(rs.getInt("bpoint"));
				dto.setAge(rs.getInt("age"));
				dto.setGender(rs.getString("gender"));
				list.add(dto);
			}
		} catch (SQLException e) {e.printStackTrace();
		} finally {close();}
		
		return list;
	}
}
