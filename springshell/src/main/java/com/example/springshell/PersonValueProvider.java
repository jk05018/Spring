package com.example.springshell;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProvider;
import org.springframework.stereotype.Component;

@Component
public class PersonValueProvider implements ValueProvider {

	private final PersonService service;

	public PersonValueProvider(PersonService service) {
		this.service = service;
	}

	@Override
	public boolean supports(MethodParameter methodParameter, CompletionContext completionContext) {
		return methodParameter.getParameterType().isAssignableFrom(Person.class);
	}

	@Override
	public List<CompletionProposal> complete(MethodParameter methodParameter, CompletionContext completionContext,
		String[] strings) {
		String currentInput = completionContext.currentWordUpToCursor();
		return this.service.findByName(currentInput)
			.stream()
			.map(p -> String.format("#%s) %s", p.getId(), p.getName()))
			.map(CompletionProposal::new)
			.collect(Collectors.toList());
	}
}
