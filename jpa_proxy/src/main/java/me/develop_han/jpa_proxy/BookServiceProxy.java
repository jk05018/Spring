package me.develop_han.jpa_proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

public class BookServiceProxy implements BookService{

	BookService bookService;

	public BookServiceProxy(BookService bookService) {
		this.bookService = bookService;
	}

	@Override
	public void rent(Book book) {
		System.out.println("aaaaaa");
		bookService.rent(book);
		System.out.println("bbbbbbb");
	}
}
