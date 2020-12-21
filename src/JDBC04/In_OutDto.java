package JDBC04;

import java.sql.Date;

public class In_OutDto {
    private int indexk;
    private int discount;
    private String booknum;
    private String personnum;
    private Date out_date;
	@Override
	public String toString() {
		return "In_OutDto [indexk=" + indexk + ", discount=" + discount + ", booknum=" + booknum + ", personnum="
				+ personnum + ", out_date=" + out_date + "]";
	}
	public int getIndexk() {
		return indexk;
	}
	public void setIndexk(int indexk) {
		this.indexk = indexk;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public String getBooknum() {
		return booknum;
	}
	public void setBooknum(String booknum) {
		this.booknum = booknum;
	}
	public String getPersonnum() {
		return personnum;
	}
	public void setPersonnum(String personnum) {
		this.personnum = personnum;
	}
	public Date getOut_date() {
		return out_date;
	}
	public void setOut_date(Date out_date) {
		this.out_date = out_date;
	}
}
