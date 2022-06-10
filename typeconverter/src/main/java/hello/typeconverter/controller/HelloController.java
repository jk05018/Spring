package hello.typeconverter.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/hello-v1")
	public String helloV1(HttpServletRequest request){
		String data = request.getParameter("data"); // 문자 타입으로 조회
		Integer intValue = Integer.valueOf(data);
		System.out.println("Int value = " + intValue);

		return "ok";
	}
}
