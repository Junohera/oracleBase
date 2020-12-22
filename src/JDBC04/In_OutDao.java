package JDBC04;

import java.sql.Connection;
import java.sql.Date;
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
	
	public int delete(In_OutDto dto) {
		int result = 0;
		
		String sql = "DELETE FROM IN_OUT WHERE TO_CHAR(OUT_DATE, 'YYYY-MM-DD') = TO_CHAR(?, 'YYYY-MM-DD') AND INDEXK = ?";
		con = getConnection();
		try {
			ps = con.prepareStatement(sql);
			ps.setDate(1, dto.getOut_date());
			ps.setInt(2, dto.getIndexk());
			result = ps.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();
		} finally {close();}
		
		return result;
	}
	
	public int update(In_OutDto dto) {
		int result = 0;
		In_OutDto org = null;
		String sql = "";
		
		con = getConnection();
		try {
			/* 수정대상값 조회 */
			sql = "SELECT * FROM IN_OUT"
					+ " WHERE TO_CHAR(OUT_DATE, 'YYYY-MM-DD') = TO_CHAR(?, 'YYYY-MM-DD') AND INDEXK = ?";
			ps = con.prepareStatement(sql);
			ps.setDate(1, dto.getOut_date());
			ps.setInt(2, dto.getIndexk());
			System.out.println("BEFORE EXECUTE QUERY: " + dto);
			rs = ps.executeQuery();
			if (rs.next()) {
				org = new In_OutDto();
				org.setIndexk(rs.getInt("INDEXK"));
				org.setDiscount(rs.getInt("DISCOUNT"));
				org.setBooknum(rs.getString("BOOKNUM"));
				org.setPersonnum(rs.getString("PERSONNUM"));
				org.setOut_date(rs.getDate("OUT_DATE"));
			} else {
				System.out.println("수정하려는 데이터가 존재하지않습니다.");
				return 0;
			}
			System.out.println("org: #############\n" + org);
			ps.close();
			rs.close();
			
			/* dto의 멤버변수중 OUT_DATE와 INDEXK의 값을 제외하고 가지고있는 항목을 org에 덮어쓰기 */
			org.setOut_date(dto.getOut_date());
			org.setIndexk(dto.getIndexk());
			if (!dto.getBooknum().equals("")) org.setBooknum(dto.getBooknum());
			if (!dto.getPersonnum().equals("")) org.setPersonnum(dto.getPersonnum());
			if (dto.getDiscount() != 0) org.setDiscount(dto.getDiscount());
			
			/* 수정대상건을 이용한 수정작업 */
			sql = "UPDATE IN_OUT SET "
					+ "BOOKNUM = ?, PERSONNUM = ?, DISCOUNT = ? WHERE TO_CHAR(OUT_DATE, 'YYYY-MM-DD') = TO_CHAR(?, 'YYYY-MM-DD')"
					+ " AND INDEXK = ?";
			
			ps = con.prepareStatement(sql);
			ps.setString(1, org.getBooknum());
			ps.setString(2, org.getPersonnum());
			ps.setInt(3,  org.getDiscount());
			ps.setDate(4, org.getOut_date());
			ps.setInt(5, org.getIndexk());
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int insert(In_OutDto dto) {
		int result = 0;
		int cnt = 0;
		String sql = "";
		con = getConnection();
		
		try {
			// 도서 예외
			sql = "SELECT BOOKNUM FROM BOOKLIST";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			cnt = 0;
			while(rs.next()) {
				if (rs.getString("BOOKNUM").equals(dto.getBooknum())) {
					cnt++;
				}
			}
			if (cnt == 0) {
				System.out.println("도서목록에 없는 도서코드입니다.");
				return 0;
			}
			ps.close();
			rs.close();
			
			// 회원 예외
			sql = "SELECT PERSONNUM FROM PERSON";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			cnt = 0;
			while(rs.next()) {
				if (rs.getString("PERSONNUM").equals(dto.getPersonnum())) {
					cnt++;
				}
			}
			if (cnt == 0) {
				System.out.println("존재하지않는 회원입니다.");
				return 0;
			}
			ps.close();
			rs.close();
			
			// INDEXK 설정
			sql = "SELECT MAX(INDEXK) AS MAX_INDEX FROM IN_OUT";
			sql += " WHERE TO_CHAR(OUT_DATE, 'yyyy-mm-dd') = TO_CHAR(SYSDATE, 'yyyy-mm-dd')";
			
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			int indexk = 0;
			if (rs.next()) {
				indexk = rs.getInt("MAX_INDEX") + 1;
			} else {
				indexk = 1;
			}
			ps.close();
			
			// INSERT문 실행
			sql = "INSERT INTO IN_OUT ";
			sql += "(INDEXK, DISCOUNT, BOOKNUM, PERSONNUM, OUT_DATE) VALUES ";
			sql += "(?, ?, ?, ?, SYSDATE) ";
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, indexk);
			ps.setInt(2, dto.getDiscount());
			ps.setString(3, dto.getBooknum());
			ps.setString(4, dto.getPersonnum());

			result = ps.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();
		} finally {close();}
		
		return result;
	}
	
	public ArrayList<In_OutDto> select() {
		ArrayList<In_OutDto> list = new ArrayList<In_OutDto>();
		con = getConnection();
		String sql = "SELECT * FROM IN_OUT ORDER BY TO_CHAR(OUT_DATE, 'YYYY-MM-DD') DESC, INDEXK ASC";
		
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
