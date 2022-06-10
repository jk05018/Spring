package hello.typeconverter.converter;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ConverterTest {

	@Test
	void stringToInteger() {
		final StringToIntegerConverter converter = new StringToIntegerConverter();
		final Integer result = converter.convert("10");

		Assertions.assertThat(result).isEqualTo(10);
	}
}
