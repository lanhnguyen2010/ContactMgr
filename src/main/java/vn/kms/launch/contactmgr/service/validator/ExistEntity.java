package vn.kms.launch.contactmgr.service.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import vn.kms.launch.contactmgr.domain.contact.Company;

@Documented
@Constraint(validatedBy = ExistEntityValidate.class)
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface ExistEntity {

	String message() default "{validation.existEntity.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	@Target({ METHOD, FIELD})
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		ExistEntity[] value();
	}

	Class<Company> type();
}
