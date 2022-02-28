package org.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD}) // 어디에 붙일 수 있는
@Inherited //상속받는 클래스들까지 이 annotation을 상속받는다.
public @interface MyAnnotation {
	// 기본적으로 annotation은 주석과 같은 취급을 받는다.
	// 바이트 코드를 로딩했을 때 메모리 상에서는 남지 않는다.

	// Retension을 통해 RUNTIME 까지 유지할 수 있다.
	// 지정하지 않는다면 사라진다.지

	// value라는 값으로 사용하면 MyAnnotation을 사용할 때 value = "seungahn" 필요 없다 그냥 "seunghan"
	// 여러개의 속성을 사용하려면 각각 이름을 지정해야 한다. value = "senghan", number = 100;
	String value();

	int number() default 100;
}
