package JDBC04;

import java.util.ArrayList;
import java.util.Scanner;

import JDBC03.BookDriver;

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
			case "4": /*updateDate();*/ break;
			case "5": /*deleteData();*/ break;
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

	public static void insertData() {
		// 날짜는 오늘날짜, 순번 : 오늘날짜에서 가장 큰 순번 + 1 -> 임의의 숫자를 넣되
		// 날짜 + 순번이 중복되지 않게 입력
		In_OutDto dto = new In_OutDto();
		Scanner sc = new Scanner(System.in);
		// System.out.print("순번을 입력하세요"); // 시퀀스로 대체
		// dto.setIndexk(Integer.parseInt(sc.nextLine())); // 시퀀스로 대체
		BookDriver bd = new BookDriver();
		bd.selectData();
		System.out.print("도서번호를 입력하세요: ");
		dto.setBooknum(sc.nextLine());
		PersonDriver pd = new PersonDriver();
		pd.selectData();
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
