package me.develop_han.jpa_proxy;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

class BookRepositoryTest {
	// 우리가 컴파일 타임때 만든 프록시 예제
	// BookService bookService = new BookServiceProxy(new DefaultBookService());

	// 프록시 클래스를 매번 만드는 수고는 줄어들지만 문제는 이 invocation handler 자체가 유연하지 않다.
	// Spring AOP가 다 뜯어 고침 -> Spring AOP 프록시 기반 AOP?
	// 자세한 내용은 토비의 스프링 Spring AOP 참고
	BookService bookService = (BookService)Proxy.newProxyInstance(
		BookService.class.getClassLoader(), // book Service의 클래스 로더
		new Class[] {BookService.class}, //클래스 배열 인터페이스 목록어떤 인스턴스 타입의 프록시인가
		new InvocationHandler() { // 프록시의 메서드가 호출될때 어떻게 처리할 것인가
			BookService bookService = new DefaultBookService();
			@Override
			public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
				System.out.println("aaaaa");
				Object invoke = method.invoke(bookService, objects);
				System.out.println("bbbbb");
				return invoke;
			}
		});


	@Test
	public void di() throws Exception{
		Book book = new Book();
		book.setTitle("spring");
		bookService.rent(book);
		bookService.rentBook(book);
	}


}
