package net.etfbl.model;

public class Reservation {
	private Integer id;
	private User member;
	private Book book;
	private String date;
	private String status;
	public Reservation(int i, User s1, Book b1, String string, String string2) {
		id = i;
		member = s1;
		book = b1;
		date = string;
		status = string2;
	}
	public Reservation() {
		// TODO Auto-generated constructor stub
	}
	
	public Reservation(int id2) {
		this.id = id2;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getMember() {
		return member;
	}
	public void setMember(User member) {
		this.member = member;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Reservation [id=" + id + ", member=" + member + ", book=" + book + ", date=" + date + ", status="
				+ status + "]";
	}
	
	
	
	
	
	
}
