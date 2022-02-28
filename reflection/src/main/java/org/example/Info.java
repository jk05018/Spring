package org.example;

public class Info {

	public static String A = "A";

	private String B = "B";

	public Info() {
	}

	public void c() {
		System.out.println("C");
	}

	public Info(String b) {
		B = b;
	}

	public int d(int left, int right) {
		return left + right;
	}
}
