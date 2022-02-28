package org.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InfoApp {
	public static void main(String[] args) throws
		ClassNotFoundException,
		NoSuchMethodException,
		IllegalAccessException,
		InvocationTargetException,
		InstantiationException, NoSuchFieldException {
		Class<?> infoClass = Class.forName("org.example.Info"); // 클래스 로딩이 발생함.
		// 클래스 파일에서 인스턴스를 생성하는 방법 -> constructor를 사용하는 방법
		Constructor<?> constructor = infoClass.getConstructor(null);
		Info info = (Info)constructor.newInstance();
		System.out.println(info);

		// 생성자에 매개변수가 있다면
		Constructor<?> constructor2 = infoClass.getConstructor(String.class);
		Info info2 = (Info)constructor2.newInstance("seunghan");
		System.out.println(info2);

		// A라는 static 필드 가져오기
		Field a = Info.class.getDeclaredField("A");
		// 값 꺼내기 -> 각 인스턴스마다 다른게 아닌 static 값을 넘겨줄 필요가 없다.
		System.out.println(a.get(null));

		a.set(null, "AAAAAAA"); // 변경
		System.out.println(a.get(null));

		// 인스턴스 값을 가져오려면 인스턴스가 만들어 졌어야함
		Field B = Info.class.getDeclaredField("B");
		//info2 의 인스턴스 값 가져오기
		B.setAccessible(true);
		System.out.println(B.get(info2));
		B.set(info2, "BBBBBBBB");
		System.out.println(B.get(info2));

		//메서드도 비슷
		Method c = Info.class.getDeclaredMethod("c");
		// private 이기 때문에 리플렉션으로 access 허용
		c.setAccessible(true);
		c.invoke(info2);

		//public class and parameter 있음
		Method d = Info.class.getDeclaredMethod("d", int.class, int.class); // 메서드를 넘길 때도 파라미터 값을 줘야한다?
		d.invoke(info2, 2, 3);
	}
}
