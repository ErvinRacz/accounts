package test.ervinracz.accounts.accounts.domain;

import java.util.Currency;

public interface ExchangeRateCacheService {
  Float getExchangeRate(final Currency baseCurrency, final Currency changeCurrency);
}
