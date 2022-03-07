package test.ervinracz.accounts.accounts;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.ervinracz.accounts.accounts.domain.services.AccountService;
import test.ervinracz.accounts.accounts.domain.services.ExchangeRatesApiClient;

@SpringBootTest
class AccountsApplicationTests {

  @Autowired private AccountService accountService;
  @Autowired private ExchangeRatesApiClient exchangeRatesApiClient;

  @Test
  void contextLoads() {
    assertThat(accountService).isNotNull();
    assertThat(exchangeRatesApiClient).isNotNull();
  }
}
