package test.ervinracz.accounts.accounts.domain.exceptions;

import java.util.Currency;
import lombok.Getter;

@Getter
public class ExchangeRateNotAvailableForCurrenciesException extends RuntimeException {

  public ExchangeRateNotAvailableForCurrenciesException(
      Currency baseCurrency, Currency changeCurrency) {
    super(
        String.format(
            "Exchange rate not available for base currency {%s} and change currency {%s}",
            baseCurrency.getCurrencyCode(), changeCurrency.getCurrencyCode()));
  }
}
