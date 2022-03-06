package test.ervinracz.accounts.accounts.domain.dtos;

import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class ExchangeRatesApiResponse {
  boolean success;
  Long timestamp;
  String base;
  String date;
  Map<String, Float> rates;

  /**
   * @param currencyCode ISO 4...
   */
  public Optional<Float> getRate(String currencyCode) {
    return Optional.ofNullable(rates.get(currencyCode));
  }
}
