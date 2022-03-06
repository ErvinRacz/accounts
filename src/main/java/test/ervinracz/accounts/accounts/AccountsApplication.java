package test.ervinracz.accounts.accounts;

import java.util.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import test.ervinracz.accounts.accounts.domain.entities.Account;
import test.ervinracz.accounts.accounts.domain.entities.AccountRepo;
import test.ervinracz.accounts.accounts.domain.types.Balance;
import test.ervinracz.accounts.accounts.domain.types.Iban;

@EnableCaching
@EnableFeignClients
@SpringBootApplication
public class AccountsApplication {

  private static final Logger log = LoggerFactory.getLogger(AccountsApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(AccountsApplication.class, args);
  }

  @Bean
  public CommandLineRunner demo(AccountRepo repo) {
    return (args) -> {
      // save a few accounts for the demo
      Iban iban1 = Iban.of("KW81CBKU0000000000001234560101");
      repo.findByIban(iban1)
          .ifPresentOrElse(
              (account) -> log.info(String.format("Account already set: %s", account.toString())),
              () ->
                  repo.save(
                      Account.builder()
                          .iban(iban1)
                          .currency(Currency.getInstance("EUR"))
                          .balance(new Balance("100.00"))
                          .build()));

      log.info("Demo accounts have been added:");
      log.info("-------------------------------");
      repo.findAll().forEach(account -> log.info(account.toString()));
    };
  }
}
