package test.ervinracz.accounts.accounts.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import test.ervinracz.accounts.accounts.domain.dtos.ExchangeRatesApiResponse;
import test.ervinracz.accounts.accounts.domain.entities.Account;
import test.ervinracz.accounts.accounts.domain.entities.AccountRepo;
import test.ervinracz.accounts.accounts.domain.exceptions.AccountRequestedNotFoundException;
import test.ervinracz.accounts.accounts.domain.services.AccountService;
import test.ervinracz.accounts.accounts.domain.services.ExchangeRatesApiClient;
import test.ervinracz.accounts.accounts.domain.types.Balance;
import test.ervinracz.accounts.accounts.domain.types.Iban;

@SpringBootTest
class AccountServiceTest {

  private static final String IBAN_STR = "KW81CBKU0000000000001234560101";
  @MockBean AccountRepo accountRepo;
  @MockBean ExchangeRatesApiClient exchangeRatesApiClient;
  @Autowired AccountService accountService;

  @BeforeEach
  void setUp() {}

  @Test
  public void should_throw_account_not_found_exception() {
    // Action & Assertion
    assertThrows(
        AccountRequestedNotFoundException.class,
        () ->
            accountService.getBalanceInCurrency(
                Iban.of("KW81CBKU0000000000001234560101"), Currency.getInstance("HUF")));
  }

  @Test
  public void should_find_account_and_do_conversion_with_fresh_and_then_cached_rate_value() {
    // Arrangement
    when(accountRepo.findByIban(any()))
        .thenReturn(
            Optional.of(
                Account.builder()
                    .id(1L)
                    .iban(Iban.of(IBAN_STR))
                    .currency(Currency.getInstance("EUR"))
                    .balance(new Balance(100))
                    .build()));

    when(exchangeRatesApiClient.getExchangeRate(any(), any(), any()))
        .thenReturn(
            ResponseEntity.ok(
                ExchangeRatesApiResponse.builder().rates(Map.of("RON", 4.9f)).build()));

    // Action
    accountService.getBalanceInCurrency(Iban.of(IBAN_STR), Currency.getInstance("RON"));
    Balance ron =
        accountService.getBalanceInCurrency(Iban.of(IBAN_STR), Currency.getInstance("RON"));

    // Assertion
    assertEquals(0, BigDecimal.valueOf(490).compareTo(ron.getAmount()));
    Mockito.verify(exchangeRatesApiClient, times(1)).getExchangeRate(any(), any(), any());
  }
}
