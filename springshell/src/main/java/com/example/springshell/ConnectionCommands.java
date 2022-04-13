package com.example.springshell;

import java.util.List;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class ConnectionCommands {
	private final ConsoleService consoleService;
	private final PersonService service;

	public ConnectionCommands(ConsoleService consoleService, PersonService service) {
		this.consoleService = consoleService;
		this.service = service;
	}

	@ShellMethod(value = "connect to the service", key = {"link","connect"})
	public void connect(String username, String password) {
		this.service.connect(username, password);
		this.consoleService.write("connected %s", username);
	}

	@ShellMethod("disconnect from service")
	public void disconnect() {
		this.service.disconnect();
	}

	Availability connectAvailability() {
		return !this.service.isConnected() ?
			Availability.available() : Availability.unavailable("you're already connected");
	}

	Availability disconnectAvailability() {
		return this.service.isConnected() ?
			Availability.available() : Availability.unavailable("you're not connected");
	}
}
