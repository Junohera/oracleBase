package JDBC04;

import java.util.ArrayList;
import java.util.Scanner;

public class PersonDriver {

	public static void selectData() {
		PersonDao dao = new PersonDao();
		ArrayList<PersonDto> list = null;
		list = dao.select();
		System.out.println("번호%t이름%t전화번호%t생년월일%t가입일%t포인트%t나이%t성별\n");
		System.out.println("-----------------------------------------------------------------");

		for (PersonDto dto : list) {
			System.out.printf("%s\t%s\t%s\t%s\t%s%d\t%d\t%s\n"
			, dto.getPersonnum()
			, dto.getPersonname()
			, dto.getPhone()
			, dto.getBirth()
			, dto.getEnterdate()
			, dto.getBpoint()
			, dto.getAge()
			, dto.getGender()
			);
		}
	}
}
