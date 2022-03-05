package test.ervinracz.accounts.accounts.infrastructure.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import test.ervinracz.accounts.accounts.utils.IbanUtils;

public class IbanValidator implements ConstraintValidator<IbanValidation, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return IbanUtils.isValid(value);
  }
}
