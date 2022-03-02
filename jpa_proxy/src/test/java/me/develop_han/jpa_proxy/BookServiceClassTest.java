package me.develop_han.jpa_proxy;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

class BookServiceClassTest {

	@Test
	public void di() throws Exception {
		MethodInterceptor handler = new MethodInterceptor() {
			BookServiceClass bookService = new BookServiceClass();

			@Override
			public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws
				Throwable {
				if (method.getName().equals("rent")) {
					System.out.println("aaaaa");
					Object invoke = method.invoke(bookService, objects);
					System.out.println("bbbb");
					return invoke;
				}
				return null;
			}
		};

		BookServiceClass bookService = (BookServiceClass)Enhancer.create(BookServiceClass.class, handler);

		Book book = new Book();
		book.setTitle("spring");
		bookService.rent(book);
		bookService.rentBook(book);
	}

}
