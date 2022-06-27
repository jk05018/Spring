package hello.typeconverter.converter;

import static org.junit.jupiter.api.Assertions.*;

import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ConverterTest {

	@Test
	void stringToInteger() {
		final StringToIntegerConverter converter = new StringToIntegerConverter();
		final Integer result = converter.convert("10");

		Assertions.assertThat(result).isEqualTo(10);
	}

	@Test
	void StringToIpPort() {
		final StringToIpPortConverter converter = new StringToIpPortConverter();
		final IpPort ipPort = converter.convert("localhost:8080");

		Assertions.assertThat(ipPort.getIp()).isEqualTo("localhost");
		Assertions.assertThat(ipPort.getPort()).isEqualTo(8080);
	}
}
