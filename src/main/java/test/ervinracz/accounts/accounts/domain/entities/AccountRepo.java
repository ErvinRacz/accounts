package test.ervinracz.accounts.accounts.domain.entities;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import test.ervinracz.accounts.accounts.domain.types.Iban;

public interface AccountRepo extends CrudRepository<Account, Long> {

  List<Account> findByIban(Iban iban);

  Optional<Account> findById(long id);
}
