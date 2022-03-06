package test.ervinracz.accounts.accounts.domain;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import test.ervinracz.accounts.accounts.AccountsApplication;
import test.ervinracz.accounts.accounts.domain.dtos.ExchangeRatesApiResponse;

@FeignClient(name = "exchangerates", url = "http://api.exchangeratesapi.io", path = "/v1/latest")
public interface ExchangeRatesApiClient {

  Logger log = LoggerFactory.getLogger(AccountsApplication.class);

  @GetMapping
  @CircuitBreaker(name = "default", fallbackMethod = "getDefaultExchangeRate")
  ResponseEntity<ExchangeRatesApiResponse> getExchangeRate(
      @RequestParam(value = "access_key") String accessKey,
      @RequestParam("base") String base,
      @RequestParam("symbols") String symbol);

  default ResponseEntity<ExchangeRatesApiResponse> getDefaultExchangeRate(
      String accessKey, String base, String symbol, Throwable t) {
    log.debug(t.getMessage());
    return ResponseEntity.notFound().build();
  }
}
