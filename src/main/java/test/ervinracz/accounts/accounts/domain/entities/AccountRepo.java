package test.ervinracz.accounts.accounts.domain.entities;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import test.ervinracz.accounts.accounts.domain.types.Iban;

public interface AccountRepo extends JpaRepository<Account, Long> {

  Optional<Account> findByIban(Iban iban);

  @Async
  CompletableFuture<Account> findOneByIban(Iban iban);

  Optional<Account> findById(long id);
}
