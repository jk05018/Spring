package com.example.springshell;

import static java.util.stream.Collectors.*;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements InitializingBean {
	private final Map<Long, Person> people = new ConcurrentHashMap<>();
	private final AtomicBoolean connected = new AtomicBoolean();

	boolean isConnected() {
		return this.connected.get();
	}

	void connect(String isr, String pw) {
		this.connected.set(true);
	}

	void disconnect() {
		this.connected.set(false);
	}

	Person findById(Long id) {
		return this.people.get(id);
	}

	Collection<Person> findByName(String name) {
		return this.people.values()
			.stream()
			.filter(p -> p.getName().toLowerCase(Locale.ROOT).contains(name))
			.collect(toList());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		AtomicLong ids = new AtomicLong();
		final Map<Long, Person> personMap = Stream.of("Brian Dussault", "Brian Clozel", "Stephane Maldini",
			"Stephane Nicoll",
			"James Watters", "James Bayer", "Cornelia Davis", "Madhura Bhave")
			.map(name -> new Person(ids.incrementAndGet(), name))
			.collect(toMap(p -> p.getId(), p -> p));

		this.people.putAll(personMap);

	}
}
