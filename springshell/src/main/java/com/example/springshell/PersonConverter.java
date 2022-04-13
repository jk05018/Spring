package com.example.springshell;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

public class PersonConverter implements Converter<String, Person> {

	private final PersonService service;
	// (#42) foo bar
	private final Pattern pattern = Pattern.compile("\\(#(\\d+)\\).*");

	public PersonConverter(PersonService service) {
		this.service = service;
	}

	@Nullable
	@Override
	public Person convert(String source) {
		Matcher matcher = this.pattern.matcher(source);
		if (matcher.find()) {
			String group = matcher.group(1);
			if(StringUtils.hasLength(group)){
				final long id = Long.parseLong(group);
				return service.findById(id);
			}
		}
		return null;
	}
}
