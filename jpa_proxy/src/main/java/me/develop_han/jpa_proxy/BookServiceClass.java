package me.develop_han.jpa_proxy;

public class BookServiceClass {

	public void rent(Book book) {
		System.out.println("rent :" + book.getTitle());
	}

	public void rentBook(Book book) {
		System.out.println("rentBook : " + book.getTitle());
	}
}
