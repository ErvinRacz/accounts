package test.ervinracz.accounts.accounts.domain.entities;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import test.ervinracz.accounts.accounts.domain.types.Iban;

public interface AccountRepo extends JpaRepository<Account, Long> {

  Optional<Account> findByIban(Iban iban);

  Optional<Account> findById(long id);
}
