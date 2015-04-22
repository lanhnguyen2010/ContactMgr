package vn.kms.launch.contactmgr.service.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ExistEntityValidate.class)
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface ExistEntity {

    String message() default "{validation.existEntity.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    Class<?> type();
    
    @Target({ METHOD, FIELD})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        ExistEntity[] value();
    }
}
