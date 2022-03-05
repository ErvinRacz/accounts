package test.ervinracz.accounts.accounts;

import java.util.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import test.ervinracz.accounts.accounts.domain.entities.Account;
import test.ervinracz.accounts.accounts.domain.entities.AccountRepo;
import test.ervinracz.accounts.accounts.domain.types.Balance;
import test.ervinracz.accounts.accounts.domain.types.Iban;

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
      repo.save(Account.builder().iban(Iban.of("KW81CBKU0000000000001234560101"))
          .currency(Currency.getInstance("RON")).balance(new Balance()).build());

      log.info("Demo account have been added:");
      log.info("-------------------------------");
      repo.findAll().forEach(account -> log.info(account.toString()));
    };
  }
}
