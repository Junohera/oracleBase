package JDBC05;

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
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	public int delete(String personnum) {
		int result = 0;
		
		String sql = "DELETE FROM PERSON WHERE PERSONNUM = ?";
		con = getConnection();
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, personnum);
			result = ps.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();
		} finally {close();}
		
		return result;
	}

	public int insert(PersonDto dto) {
		int result = 0;
		int cnt = 0;
		String sql = null;
		
		if (dto.getEnterdate() != null) {
			sql = "INSERT INTO PERSON (PERSONNUM, PERSONNAME, PHONE, BIRTH, ENTERDATE, BPOINT, AGE, GENDER) VALUES (PERSON_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";	
		} else {
			sql = "INSERT INTO PERSON (PERSONNUM, PERSONNAME, PHONE, BIRTH, BPOINT, AGE, GENDER) VALUES (PERSON_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";	
		}
		
		try {
			con = getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(++cnt, dto.getPersonname());
			ps.setString(++cnt, dto.getPhone());
			ps.setDate(++cnt, dto.getBirth());
			if (dto.getEnterdate() != null) {
				ps.setDate(++cnt, dto.getEnterdate());	
			}
			ps.setInt(++cnt, dto.getBpoint());
			ps.setInt(++cnt, dto.getAge());
			ps.setString(++cnt, dto.getGender());

			result = ps.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();
		} finally {close();}
		
		return result;
	}

	public int update(PersonDto dto) {
		PersonDto org = null;
		int result = 0;
		String sql = null;
		con = getConnection();
		try {
			sql = "SELECT * FROM PERSON WHERE PERSONNUM = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getPersonnum());
			rs = ps.executeQuery();
			if (rs.next()) {
				org = new PersonDto();
				org.setPersonnum(rs.getString("PERSONNUM"));
				org.setPersonname(rs.getString("PERSONNAME"));
				org.setPhone(rs.getString("PHONE"));
				org.setBirth(rs.getDate("BIRTH"));
				org.setEnterdate(rs.getDate("ENTERDATE"));
				org.setBpoint(rs.getInt("BPOINT"));
				org.setAge(rs.getInt("AGE"));
				org.setGender(rs.getString("GENDER"));
			} else {
				close();
				System.out.println("수정하려는 데이터가 존재하지않습니다.");
				return 0;
			}
			ps.close();
			rs.close();

			if (!dto.getPersonname().equals("")) org.setPersonname(dto.getPersonname());
			if (!dto.getPhone().equals("")) org.setPhone(dto.getPhone());
			if (!dto.getGender().equals("")) org.setGender(dto.getGender());

			if (dto.getBirth() != null) org.setBirth(dto.getBirth());
			if (dto.getEnterdate() != null) org.setEnterdate(dto.getEnterdate());

			if (dto.getBpoint() != 0) org.setBpoint(dto.getBpoint());
			if (dto.getAge() != 0) org.setAge(dto.getAge());
			
			sql = "UPDATE PERSON SET "
			+ " PERSONNAME = ?"
			+ " , PHONE = ?"
			+ " , BIRTH = ?"
			+ " , ENTERDATE = ?"
			+ " , BPOINT = ?"
			+ " , AGE = ?"
			+ " , GENDER = ?"
			+ " WHERE PERSONNUM = ?";
			ps = con.prepareStatement(sql);

			ps.setString(1, org.getPersonname());
			ps.setString(2, org.getPhone());
			ps.setDate(3, org.getBirth());
			ps.setDate(4, org.getEnterdate());
			ps.setInt(5, org.getBpoint());
			ps.setInt(6, org.getAge());
			ps.setString(7, org.getGender());
			ps.setString(8, dto.getPersonnum());

			result = ps.executeUpdate();

		} catch (SQLException e) {e.printStackTrace();
		} finally {close();}
		
		return result;
	}
	
	public ArrayList<PersonDto> select() {
		ArrayList<PersonDto> list = new ArrayList<PersonDto>();
		con = getConnection();
		String sql = "SELECT * FROM PERSON ORDER BY PERSONNUM DESC";
		
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
