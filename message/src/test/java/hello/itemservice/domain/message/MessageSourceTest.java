package hello.itemservice.domain.message;

import static org.assertj.core.api.Assertions.*;

import java.util.Locale;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

@SpringBootTest
public class MessageSourceTest {

	@Autowired
	MessageSource messageSource;

	@Test
	void helloMessage() {
		final String result = messageSource.getMessage("hello", null, null);
		assertThat(result).isEqualTo("안녕");
	}

	@Test
	void NotFoundMessageCode() {
		assertThatThrownBy(() -> messageSource.getMessage("no-code", null, null)).isInstanceOf(
			NoSuchMessageException.class);
	}

	@Test
	void notFOundMessageCodeDefaultMessage() {
		final String default_message = messageSource.getMessage("no-code", null, "default message", null);
		assertThat(default_message).isEqualTo("default message");
	}

	@Test
	void argumentMessage() {
		final String message = messageSource.getMessage("hello.name", new Object[] {"Spring"}, null);
		assertThat(message).isEqualTo(message);
	}

	@Test
	void defaultLang() {
		assertThat(messageSource.getMessage("hello", null, null)).isEqualTo("안녕");
		assertThat(messageSource.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
	}

	@Test
	void enLang() {
		assertThat(messageSource.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
	}
}
