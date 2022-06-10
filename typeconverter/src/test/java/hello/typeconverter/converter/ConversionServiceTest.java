package hello.typeconverter.converter;

import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

public class ConversionServiceTest {

	@Test
	void conversiojnService() {
		final DefaultConversionService defaultConversionService = new DefaultConversionService();
		defaultConversionService.addConverter(new StringToIntegerConverter());
		defaultConversionService.addConverter(new IntegerToStringConverter());

		final Integer resuylt = defaultConversionService.convert("10", Integer.class);
		System.out.println("result = " + resuylt);
	}
}
