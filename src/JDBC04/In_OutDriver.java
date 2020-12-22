package JDBC04;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import JDBC03.BookDriver;
import JDBC05.PersonDriver;

public class In_OutDriver {

	public static void main(String[] args) {
		String choice = null;
		boolean first = true;
		
		do {
			if (first) {
				choice = home();
				first = false;
			}
			
			switch (choice) {
			case "2": insertData(); break;
			case "3": selectData(); break;
			case "4": updateDate(); break;
			case "5": deleteData(); break;
			}
			
			choice = home();
		} while (!"1".equals(choice));
		
		System.out.println("-------프로그램 종료-------");
	}

	public static String home() {
		Scanner sc = new Scanner(System.in);
		System.out.println("\n*** 메뉴 선택 ***");
		System.out.print("1. 프로그램종료 ");
		System.out.print("2. 데이터추가 ");
		System.out.print("3. 데이터열람 ");
		System.out.print("4. 데이터수정 ");
		System.out.println("5. 데이터 삭제 ");
		System.out.print(">>메뉴선택 : ");
		return sc.nextLine();
	}
	
	public static void deleteData() {
		In_OutDto dto = new In_OutDto();
		Scanner sc = new Scanner(System.in);
		In_OutDao dao = new In_OutDao();
		
		selectData();
		
		System.out.println("삭제할 매출일자를 입력하세요('yyyy-MM-dd')");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date uDate = null;
		
		while(true) { // utilDate형으로 포맷이 맞을때까지 입력, 
			try {
				uDate = sdf.parse(sc.nextLine());
				break;
			} catch (ParseException e) {
				System.out.println("잘못 입력하셨습니다. 다시 입력하세요('yyyy-MM-dd')");
				e.printStackTrace();
			}
		}
		
		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		dto.setOut_date(sDate); // dto에 저장
		
		String in = "";
		int result = 0;
		System.out.print("삭제할 매출의 순번을 입력하세요 - 필수");
		while(true) {
			if (!(in = sc.nextLine()).equals("")) {
				dto.setIndexk(Integer.parseInt(in));
				result = dao.delete(dto);
				break;
			} else {
				System.out.println("삭제할 매출의 순번을 입력해주세요 - 필수");
			}
		}
		if (result == 1) {
			System.out.println("삭제 성공 :)");
		} else {
			System.out.println("삭제 실패 :(");
		}
	}
	
	public static void updateDate() {
		In_OutDto dto = new In_OutDto();
		Scanner sc = new Scanner(System.in);
		In_OutDao dao = new In_OutDao();
		
		String in;
		
		// 문자로 입력받은 날짜 -> java.util.Date로 형변환 -> java.sql.Date로 변환 -> dto.setOut_date()로 저장
		System.out.print("수정하고자하는 매출의 날짜를 입력하세요('yyyy-MM-dd')");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date uDate = null;
		
		while(true) { // utilDate형으로 포맷이 맞을때까지 입력, 
			try {
				uDate = sdf.parse(sc.nextLine());
				break;
			} catch (ParseException e) {
				System.out.println("잘못 입력하셨습니다. 다시 입력하세요('yyyy-MM-dd')");
				e.printStackTrace();
			}
		}
		java.sql.Date sDate = new java.sql.Date(uDate.getTime()); // utilDate의 시간을 반환받아 sqlDate로 변경
		dto.setOut_date(sDate); // dto에 저장
		
		selectData();
		System.out.print("수정하고자 하는 매출의 순번을 입력하세요 - 필수");
		if ((in = sc.nextLine()).equals("")) {
			dto.setIndexk(0);
		} else {
			dto.setIndexk(Integer.parseInt(in));
		}
		
		new BookDriver().selectData();
		System.out.print("수정할 도서의 도서번호를 선택하세요(수정하지않으려면 Enter):");
		dto.setBooknum(sc.nextLine());
		
		new PersonDriver().selectData();
		System.out.print("수정할 도서의 회원번호를 선택하세요(수정하지않으려면 Enter):");
		dto.setPersonnum(sc.nextLine());
		
		System.out.print("수정하고자하는 할인금액을 입력하세요(수정하지않으려면 Enter):");
		if ((in = sc.nextLine()).equals("")) {
			dto.setDiscount(0);
		} else {
			dto.setDiscount(Integer.parseInt(in));
		}
		
		int result = dao.update(dto);
		
		if (result == 1) {
			System.out.println("수정 성공 :)");
		} else {
			System.out.println("수정 실패 :(");
		}
	}

	public static void insertData() {
		In_OutDto dto = new In_OutDto();
		Scanner sc = new Scanner(System.in);
		new BookDriver().selectData();
		System.out.print("도서번호를 입력하세요: ");
		dto.setBooknum(sc.nextLine());
		new PersonDriver().selectData();
		System.out.print("회원번호를 입력하세요: ");
		dto.setPersonnum(sc.nextLine());
		System.out.print("할인금액을 입력하세요: ");
		dto.setDiscount(Integer.parseInt(sc.nextLine()));
		
		In_OutDao dao = new In_OutDao();
		int result = dao.insert(dto);
		
		if (result == 1) {
			System.out.println("추가 성공 :)");
			selectData();
		} else {
			System.out.println("추가 실패 :(");
		}
	}
	
	public static void selectData() {
		In_OutDao iDao = new In_OutDao();
		ArrayList<In_OutDto> list = null;
		list = iDao.select();
		System.out.println("\n-----------------------------------------------------------------");
		System.out.println("순번\t대여일자\t\t도서번호\t회원번호\t할인금액");
		System.out.println("-----------------------------------------------------------------");

		for (In_OutDto dto : list) {
			System.out.printf("%d\t%s\t%s\t\t%s\t\t%d\n"
				, dto.getIndexk()
				, dto.getOut_date()
				, dto.getBooknum()
				, dto.getPersonnum()
				, dto.getDiscount()
			);
		}
	}
}
