package hello.itemservice.domain.item;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;

class BeanValidationTestTest {

	@Test
	void beanValidation() {
		final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		final Validator validator = factory.getValidator();

		final Item item = new Item();
		item.setItemName(" ");
		item.setPrice(0);
		item.setQuantity(10000);

		final Set<ConstraintViolation<Item>> violations = validator.validate(item);
		for (ConstraintViolation<Item> violation : violations) {
			System.out.println("violation = " + violation);
			System.out.println("violation.message = " + violation.getMessage());
		}
	}
}
