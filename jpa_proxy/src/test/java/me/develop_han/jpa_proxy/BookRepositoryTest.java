package me.develop_han.jpa_proxy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

class BookRepositoryTest {

	BookService bookService = new BookServiceProxy(new DefaultBookService());

	@Test
	public void di() throws Exception{
		Book book = new Book();
		book.setTitle("spring");
		bookService.rent(book);
	}


}
