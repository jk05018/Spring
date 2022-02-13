package me.seunghan.security_jwt.filter;

import java.io.IOException;

import javax.servlet.Filter; //servlet 잘 알아두기!!
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyFilter1 implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		log.info("filter1");
		chain.doFilter(request, response);
	}
}
