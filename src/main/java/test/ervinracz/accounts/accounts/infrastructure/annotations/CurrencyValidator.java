package test.ervinracz.accounts.accounts.infrastructure.annotations;

import java.util.Currency;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrencyValidator implements ConstraintValidator<CurrencyValidation, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return Currency.getAvailableCurrencies().stream()
        .anyMatch(currency -> currency.getCurrencyCode().equals(value));
  }
}
