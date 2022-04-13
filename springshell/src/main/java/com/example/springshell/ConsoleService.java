package com.example.springshell;

import java.io.PrintStream;

import org.springframework.stereotype.Service;

@Service
public class ConsoleService {
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_RESET = "\u001B[0m";
	private final PrintStream out = System.out;

	public void write (String msg, String ... args){
		this.out.print("> ");
		this.out.print(ANSI_YELLOW);
		this.out.printf(msg,(Object[])args);
		this.out.print(ANSI_RESET);
		this.out.println();
	}
}
