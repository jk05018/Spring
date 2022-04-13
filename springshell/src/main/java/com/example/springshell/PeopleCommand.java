package com.example.springshell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class PeopleCommand {

	private final ConsoleService consoleService;

	public PeopleCommand(ConsoleService consoleService) {
		this.consoleService = consoleService;
	}

	@ShellMethod("interact with the directory")
	public void directory(@ShellOption(valueProvider = PersonValueProvider.class) Person person) { // we need to teach how to do that
		this.consoleService.write("working with %s", person.getName());

	}
}
