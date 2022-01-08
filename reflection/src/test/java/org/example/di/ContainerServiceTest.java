package org.example.di;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.TestCase;

public class ContainerServiceTest {

	@Test
	public void getObject_BookRepository() throws Exception{
		BookRepository bookRepository = ContainerService.getObject(BookRepository.class);
		assertNotNull(bookRepository);
	}

	@Test
	public void getObject_BookService() throws Exception{
		BookService bookService = ContainerService.getObject(BookService.class);
		assertNotNull(bookService);
		//@Inject에 아무것도 해 주지 않았기 때문에 에러 발생하였음
		assertNotNull(bookService.bookRepository);
	}
}
