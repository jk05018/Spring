package com.example.springshell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class Practice {
	@ShellMethod("Add tow integers together.")
	public int add(int a, int b) {
		return a + b;
	}
}
