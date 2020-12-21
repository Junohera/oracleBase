package JDBC03;

import java.util.ArrayList;
import java.util.Scanner;

public class BookDriver {
	
	private static int tryCount = 0;
	private static int insertCount = 0;
	private static int selectCount = 0;
	private static int updateCount = 0;
	private static int deleteCount = 0;
	private static int homeCount = 0;

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
			case "3": selectData(); selectCount++; break;
			case "4": updateDate(); break;
			case "5": deleteData(); break;
			}
			
			tryCount++;
			
			choice = home();
		} while (!"1".equals(choice));
		
		System.out.println("-------프로그램 종료-------");
		System.out.println("프로그램 총 사용횟수 : " + (insertCount + selectCount + updateCount + deleteCount));
		System.out.printf("추가[%d], 수정:[%d], 목록[%d], 삭제[%d]\n", insertCount, updateCount, selectCount, deleteCount);
		System.out.println("------------------------");
	}
	
	public static String home() {
		homeCount++;
		
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
		BookDto book = new BookDto();
		Scanner sc = new Scanner(System.in);
		BookDao bdao = new BookDao();

		selectData(); // 삭제값 참고용
		System.out.print("삭제할 도서번호를 입력하세요: ");
		book.setBooknum(sc.nextLine());
		
		int result = bdao.delete(book);

		if (result == 1) {
			deleteCount++;
			System.out.println("삭제 성공 :)");
			selectData(); // 삭제확인용 목록조회
		} else {
			System.out.println("삭제 실패 :(");
		}
	}
	
	public static void updateDate() {
		BookDto book = new BookDto();
		Scanner sc = new Scanner(System.in);
		BookDao bdao = new BookDao();

		selectData(); // 수정값 참고용

		// 수정할 사항이 없는 필드(null - 입력하지않음)은 수정안함.
		// 수정값을 입력한 필드만 수정
		String in;

		System.out.print("수정할 도서번호를 입력하세요(필수): ");
		book.setBooknum(sc.nextLine());

		System.out.print("제목 입력하세요(수정하지 않으려면 Enter): ");
		book.setSubject(sc.nextLine());

		System.out.print("출판년도 입력하세요(수정하지 않으려면 Enter): ");
		if ((in = sc.nextLine()).equals("")) book.setMakeyear(0); // in에 입력받은 내용이 ""라면(아무것도 입력하지않고 엔터를 입력했다면) 0으로, 아니면 입력값으로
		else book.setMakeyear(Integer.parseInt(in));

		System.out.print("입고가격 입력하세요(수정하지 않으려면 Enter): ");
		if ((in = sc.nextLine()).equals("")) book.setInprice(0); // in에 입력받은 내용이 ""라면(아무것도 입력하지않고 엔터를 입력했다면) 0으로, 아니면 입력값으로
		else book.setInprice(Integer.parseInt(in));

		System.out.print("출고가격 입력하세요(수정하지 않으려면 Enter): ");
		if ((in = sc.nextLine()).equals("")) book.setOutprice(0); // in에 입력받은 내용이 ""라면(아무것도 입력하지않고 엔터를 입력했다면) 0으로, 아니면 입력값으로
		else book.setOutprice(Integer.parseInt(in));

		System.out.print("등급 입력하세요(수정하지 않으려면 Enter): "); // 입력값없으면 null, 있으면 입력값으로
		book.setGrade(sc.nextLine());

		int result = bdao.update(book);

		if (result == 1) {
			updateCount++;
			System.out.println("수정 성공 :)");
			findData(book.getBooknum()); // 수정확인용 단건조회
		} else {
			System.out.println("수정 실패 :(");
		}

	}
	
	public static void insertData() {
		Scanner sc = new Scanner(System.in);
		BookDto book = new BookDto();
		System.out.print("제목 입력하세요: ");
		book.setSubject(sc.nextLine());
		System.out.print("출판년도 입력하세요: ");
		book.setMakeyear(Integer.parseInt(sc.nextLine()));
		System.out.print("입고가격 입력하세요: ");
		book.setInprice(Integer.parseInt(sc.nextLine()));
		System.out.print("출고가격 입력하세요: ");
		book.setOutprice(Integer.parseInt(sc.nextLine()));
		System.out.print("등급 입력하세요: ");
		book.setGrade(sc.nextLine());

		System.out.println(book);
		BookDao bdao = new BookDao();
		int result = bdao.insert(book);
		
		if (result == 1) {
			insertCount++;
			System.out.println("추가 성공 :)");
			selectData();
		} else {
			System.out.println("추가 실패 :(");
		}
	}
	
	public static void selectData() {
		// 데이터의 열람
		// 1. 데이터 열람은 데이터베이스에서 레코드 목록을 조회하고 그 결과를 전달받아 화면에 표시하는 역할입니다.
		// 2. 데이터 베이스 레코드 목록을 조회하는 역할은 다른 클래스객체의 메서드를 실행시켜서 전달 받습니다.
		// 3. 클래스의 이름은 BookDao이고 그 안에서 멤버 메서드 중 select() 메서드를 이용합니다.
		BookDao bdao = new BookDao();
		// 4. 리턴값은 하나의 결과행을 담을 수 있는 BookDto 클래스의 객체에 담기고, 그렇게 담긴 객체들은 ArrayList에 행의 크기만큼 담겨 리턴됩니다. (타입은 ArrayList<BookDto>)
		ArrayList<BookDto> list = null;
		list = bdao.select();
		// list 안에는 BookDto 형태의 객체들이 데이터베이스테이블의 레코드 개수만큼 들어있습니다.
		System.out.println("도서번호\t제목\t출판년도\t입고가격\t출고가격\t등급\t");
		System.out.println("-----------------------------------------------------------------");

		for (BookDto dto : list) { // list에서 꺼낸 하나의 객체는 BookDto형이며, 반복은 개수만큼
			System.out.printf("%s\t%s\t%d\t%d\t%d\t%s\t\n"
				, dto.getBooknum()
				, dto.getSubject()
				, dto.getMakeyear()
				, dto.getInprice()
				, dto.getOutprice()
				, dto.getGrade()
			);
		}
	}
	
	public static void findData(String booknum) {
		BookDao bdao = new BookDao();
		BookDto book = bdao.find(booknum);
		System.out.println("도서번호\t제목\t출판년도\t입고가격\t출고가격\t등급\t");
		System.out.println("-----------------------------------------------------------------");

		System.out.printf("%s\t%s\t%d\t%d\t%d\t%s\t\n"
			, book.getBooknum()
			, book.getSubject()
			, book.getMakeyear()
			, book.getInprice()
			, book.getOutprice()
			, book.getGrade()
		);
	}
}
