package org.example;

public class Book extends AnnotationPractice{

	private static String b = "Book";

	private static final String C = "Book";

	private String a = "a";

	public String d = "d";

	protected String e = "e";

	public Book() {
	}

	public Book(String a, String d, String e) {
		this.a = a;
		this.d = d;
		this.e = e;
	}

	private void f(){
		System.out.println("F");
	}

	public void g(){
		System.out.println("g");
	}

	public int h(){
		return 100;
	}
}
