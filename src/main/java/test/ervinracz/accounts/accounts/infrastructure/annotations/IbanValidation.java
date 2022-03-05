package test.ervinracz.accounts.accounts.infrastructure.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IbanValidator.class)
public @interface IbanValidation {

  String message() default "Invalid IBAN";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
