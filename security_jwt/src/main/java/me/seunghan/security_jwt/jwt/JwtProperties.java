package me.seunghan.security_jwt.jwt;

public class JwtProperties {
	final String SECRET = "secret";
	final int EXPIRATION_TIME = 60000*10;
	final String TOKEN_PREFIX = "Bearer ";
	final String HEADER_STRING = "Authorization";


}
