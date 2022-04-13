package com.example.springshell;

import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class ConnectedPromptProvider implements PromptProvider {
	private final PersonService service;

	public ConnectedPromptProvider(PersonService service) {
		this.service = service;
	}

	@Override
	public AttributedString getPrompt() {
		final String msg = String.format("spring service (%s) >",
			this.service.isConnected() ? "connected" : "disconnected");
		return new AttributedString(msg);
	}
}
