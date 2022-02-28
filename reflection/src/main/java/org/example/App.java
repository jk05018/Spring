package org.example;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws ClassNotFoundException {
		// 클래스 로딩이 끝나면 Class 타입의 인스턴스를 만들어서 Heap에 저장
		// 타입으로 가져오는 방법
		Class<Book> bookClass = Book.class;
		// 인스턴스로부터 가져오는 방법
		Class<? extends Book> aClass = new Book().getClass();
		// FQCN 으로부터 클래스를 가져오는 방법
		Class<?> aClass1 = Class.forName("org.example.Book");

		// 아래와 같이 클래스에 관한 정보들, Field, Method, 관련 클래스들 superclass, interface 등의 정보를 가져올 수 있다.
		// getFields() 사용하면 public만 가져온다.
		Arrays.stream(bookClass.getFields()).forEach(System.out::println);

		// getDeclaredFields() 모든 필드
		System.out.println("============================");
		Arrays.stream(bookClass.getDeclaredFields()).forEach(System.out::println);

		System.out.println("============================");
		// 모든 필드와 값을 가져오기
		Book book = new Book();
		Arrays.stream(bookClass.getDeclaredFields()).forEach(f ->
		{
			try {
				f.setAccessible(true);
				System.out.printf("%s %s\n", f, f.get(book));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		});

		// 메서드들 가져오기
		Arrays.stream(bookClass.getMethods()).forEach(System.out::println);

	}
}
