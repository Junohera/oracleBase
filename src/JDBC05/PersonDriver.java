package JDBC05;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.ParseException;

public class PersonDriver {

	public static void main(String[] args) {
		String choice = null;
		boolean first = true;

		while (!"1".equals(choice)) {
			if (first) {
				choice = answer();
				first = false;
			}
			
			switch (choice) {
			case "2": insertData(); break;
			case "3": selectData(); break;
			case "4": updateData(); break;
			case "5": deleteData(); break;
			}
			
			choice = answer();
		}

		System.out.println("-------프로그램 종료-------");
	}

	public static String answer() {
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
		PersonDto person = new PersonDto();
		PersonDao personDao = new PersonDao();
		Scanner sc = new Scanner(System.in);
		
		selectData();
		System.out.println("삭제할 회원의 회원번호를 입력하세요.");
		int result = personDao.delete(sc.nextLine());
		if (result == 1) {
			System.out.println("삭제 성공 :)");
		} else {
			System.out.println("삭제 실패 :(");
		}
	}

	public static void updateData() {
		PersonDto person = new PersonDto();
		PersonDao personDao = new PersonDao();

		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date uDate = null;
		String in = null;
		
		selectData();
		System.out.print("수정하고자하는 회원의 회원번호를 입력하세요:");
		person.setPersonnum(sc.nextLine());
		System.out.print("이름을 입력하세요 (수정하지않으려면 Enter):");
		person.setPersonname(sc.nextLine());
		System.out.print("전화번호를 입력하세요 (수정하지않으려면 Enter):");
		person.setPhone(sc.nextLine());
		System.out.print("생일을 입력하세요('yyyy-MM-dd') (수정하지않으려면 Enter):");
		if ((in = sc.nextLine()).equals("")) {
			person.setBirth(null);
		} else {
			while(true) {
				try {
					uDate = sdf.parse(in);
					break;	
				} catch (ParseException e) {
					System.out.println("잘못 입력하셨습니다. 다시 입력하세요('yyyy-MM-dd'):");
					in = sc.nextLine();
				}
			}
			person.setBirth(new java.sql.Date(uDate.getTime()));
		}
		System.out.print("등록일을 입력하세요('yyyy-MM-dd') (수정하지않으려면 Enter):");
		if ((in = sc.nextLine()).equals("")) {
			person.setEnterdate(null);
		} else {
			while(true) {
				try {
					uDate = sdf.parse(in);
					break;
				} catch (ParseException e) {
					System.out.println("잘못 입력하셨습니다. 다시 입력하세요('yyyy-MM-dd'):");
					in = sc.nextLine();
				}
			}
			person.setEnterdate(new java.sql.Date(uDate.getTime()));
		}
		System.out.print("포인트를 입력하세요 (수정하지않으려면 Enter):");
		if ((in = sc.nextLine()).equals("")) {
			person.setBpoint(0);
		} else {
			person.setBpoint(Integer.parseInt(in));
		}
		
		System.out.print("나이를 입력하세요 (수정하지않으려면 Enter):");
		if ((in = sc.nextLine()).equals("")) {
			person.setAge(0);
		} else {
			person.setAge(Integer.parseInt(in));
		}
		
		System.out.print("성별을 입력하세요(남자: M, 여자: F) (수정하지않으려면 Enter):");
		if ((in = sc.nextLine()).equals("")) {
			
		} else {
			while(true) {
				if ("M".equals(in) || "F".equals(in)) {
					break;
				} else {
					System.out.println("잘못 입력하셨습니다. 다시 입력하세요(남자: M, 여자: F):");
					in = sc.nextLine();
				}
			}
		}
		person.setGender(in);
		
		int result = personDao.update(person);

		if (result == 1) {
			System.out.println("수정 성공 :)");
			selectData();
		} else {
			System.out.println("수정 실패 :(");
		}
	}

	public static void insertData() {
		PersonDto person = new PersonDto();
		PersonDao personDao = new PersonDao();

		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date uDate = null;
		String in = null;

		System.out.print("이름을 입력하세요:");
		person.setPersonname(sc.nextLine());
		System.out.print("전화번호를 입력하세요:");
		person.setPhone(sc.nextLine());
		System.out.print("생일을 입력하세요('yyyy-MM-dd'):");
		while(true) {
			try {
				uDate = sdf.parse(sc.nextLine());
				break;
			} catch (ParseException e) {
				System.out.println("잘못 입력하셨습니다. 다시 입력하세요('yyyy-MM-dd'):");
			}
		}
		person.setBirth(new java.sql.Date(uDate.getTime()));
		System.out.print("등록일을 입력하세요('yyyy-MM-dd') 오늘날짜면 Enter:");
		if ((in = sc.nextLine()).equals("")) {
			person.setEnterdate(null);
		} else {
			while(true) {
				try {
					uDate = sdf.parse(in);
					break;
				} catch (ParseException e) {
					System.out.println("잘못 입력하셨습니다. 다시 입력하세요('yyyy-MM-dd'):");
					in = sc.nextLine();
				}
			}
			person.setEnterdate(new java.sql.Date(uDate.getTime()));
		}
		
		System.out.print("포인트를 입력하세요:");
		person.setBpoint(Integer.parseInt(sc.nextLine()));
		System.out.print("나이를 입력하세요:");
		person.setAge(Integer.parseInt(sc.nextLine()));
		System.out.print("성별을 입력하세요(남자: M, 여자: F):");
		while(true) {
			in = sc.nextLine();
			if ("M".equals(in) || "F".equals(in)) {
				break;
			} else {
				System.out.println("잘못 입력하셨습니다. 다시 입력하세요(남자: M, 여자: F):");
			}
		}
		person.setGender(in);

		int result = personDao.insert(person);

		if (result == 1) {
			System.out.println("삽입 성공 :)");
			selectData();
		} else {
			System.out.println("삽입 실패 :(");
		}
	}

	public static void selectData() {
		PersonDao personDao = new PersonDao();
		ArrayList<PersonDto> list = null;
		list = personDao.select();

		System.out.println("\n------------------------------------------------------------------------------------");
		System.out.println("번호\t이름\t전화번호\t\t생년월일\t\t가입일\t\t포인트\t나이\t성별");
		System.out.println("------------------------------------------------------------------------------------");
		for (PersonDto person : list) {
			System.out.printf("%s\t%s\t%s\t%s\t%s\t%d\t%d\t%s\n"
				, person.getPersonnum()
				, person.getPersonname()
				, person.getPhone()
				, person.getBirth()
				, person.getEnterdate()
				, person.getBpoint()
				, person.getAge()
				, person.getGender()
			);
		}
		System.out.println("------------------------------------------------------------------------------------");
	}
}
