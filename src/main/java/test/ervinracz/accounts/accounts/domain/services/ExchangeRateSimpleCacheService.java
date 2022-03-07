package test.ervinracz.accounts.accounts.domain.services;

import java.util.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import test.ervinracz.accounts.accounts.domain.exceptions.ExchangeRateNotAvailableForCurrenciesException;

@Service
@RequiredArgsConstructor
public class ExchangeRateSimpleCacheService implements ExchangeRateCacheService {

  private final ExchangeRatesApiClient exchangeRatesApiClient;

  @Value("${variables.exchange-rate-api-key}")
  private String exchangeRateApiKey;

  @Cacheable(value = "exchangeRateCache")
  public Float getExchangeRate(final Currency baseCurrency, final Currency changeCurrency) {
    var response =
        exchangeRatesApiClient.getExchangeRate(
            exchangeRateApiKey, baseCurrency.getCurrencyCode(), changeCurrency.getCurrencyCode());

    if (!response.getStatusCode().equals(HttpStatus.OK) || response.getBody() == null) {
      throw new ExchangeRateNotAvailableForCurrenciesException(baseCurrency, changeCurrency);
    }

    return response
        .getBody()
        .getRate(changeCurrency.getCurrencyCode())
        .orElseThrow(
            () -> new ExchangeRateNotAvailableForCurrenciesException(baseCurrency, changeCurrency));
  }
}
