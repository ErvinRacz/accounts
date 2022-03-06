package test.ervinracz.accounts.accounts.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.ervinracz.accounts.accounts.domain.dtos.AccountDto;
import test.ervinracz.accounts.accounts.domain.entities.AccountRepo;
import test.ervinracz.accounts.accounts.domain.exceptions.AccountRequestedNotFoundException;
import test.ervinracz.accounts.accounts.domain.types.Balance;
import test.ervinracz.accounts.accounts.domain.types.ExchangeRateCacheService;
import test.ervinracz.accounts.accounts.domain.types.Iban;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepo accountRepo;
  private final ExchangeRateCacheService exchangeRateCacheService;

  public AccountDto createAccount(final AccountDto accountDto) {
    return AccountDto.from(accountRepo.save(accountDto.toEntity()));
  }

  public List<AccountDto> getAllAccounts() {
    return accountRepo.findAll().stream().map(AccountDto::from).collect(Collectors.toList());
  }

  public Balance getBalanceInCurrency(Iban iban, Currency currency) {
    var account = accountRepo.findByIban(iban).orElseThrow(AccountRequestedNotFoundException::new);
    Float rate = exchangeRateCacheService.getExchangeRate(account.getCurrency(), currency);

    return new Balance(
        account
            .getBalance()
            .getAmount()
            .multiply(BigDecimal.valueOf(rate), new MathContext(6, RoundingMode.FLOOR)));
  }
}
