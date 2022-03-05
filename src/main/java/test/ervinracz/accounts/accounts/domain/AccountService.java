package test.ervinracz.accounts.accounts.domain;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import test.ervinracz.accounts.accounts.domain.dtos.AccountDto;
import test.ervinracz.accounts.accounts.domain.entities.AccountRepo;
import test.ervinracz.accounts.accounts.domain.types.Iban;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
  private final AccountRepo accountRepo;

  public AccountDto createAccount(final AccountDto accountDto) {
    return AccountDto.from(accountRepo.save(accountDto.toEntity()));
  }

  public List<AccountDto> getAllAccounts() {
    return accountRepo.findAll().stream().map(AccountDto::from).collect(Collectors.toList());
  }

  public void deleteAccount(final Iban iban) {
    accountRepo.delete(accountRepo.findByIban(iban).orElseThrow());
  }
}
