package org.example;

import java.util.Arrays;

@MyAnnotation("seunghan")
public class AnnotationPractice {

	public static void main(String[] args) {
		Class<AnnotationPractice> annotationPracticeClass = AnnotationPractice.class;
		Arrays.stream(annotationPracticeClass.getAnnotations()).forEach(System.out::println);

		System.out.println("=====================================");
		;
		// getAnnotations를 사용한다면 해당되는 모든 annotation을 가져오고
		Arrays.stream(MyBook.class.getAnnotations()).forEach(System.out::println);
		//만약 이 클래스에 선언되어  있는 annotation만 가져오고 싶다면 아래 declared를 사용하면 된다.
		Arrays.stream(MyBook.class.getDeclaredAnnotations()).forEach(System.out::println);

	}
}
