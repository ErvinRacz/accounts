package test.ervinracz.accounts.accounts.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import test.ervinracz.accounts.accounts.domain.dtos.AccountDto;
import test.ervinracz.accounts.accounts.domain.dtos.ExchangeRatesApiResponse;
import test.ervinracz.accounts.accounts.domain.entities.AccountRepo;
import test.ervinracz.accounts.accounts.domain.exceptions.AccountRequestedNotFoundException;
import test.ervinracz.accounts.accounts.domain.exceptions.ExchangeRateNotAvailableForCurrenciesException;
import test.ervinracz.accounts.accounts.domain.types.Balance;
import test.ervinracz.accounts.accounts.domain.types.Iban;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepo accountRepo;
  private final ExchangeRatesApiClient exchangeRatesApiClient;

  @Value("${variables.exchange-rate-api-key}")
  private String exchangeRateApiKey;

  public AccountDto createAccount(final AccountDto accountDto) {
    return AccountDto.from(accountRepo.save(accountDto.toEntity()));
  }

  public List<AccountDto> getAllAccounts() {
    return accountRepo.findAll().stream().map(AccountDto::from).collect(Collectors.toList());
  }

  public Balance getBalanceInCurrency(Iban iban, Currency currency) {
    var account = accountRepo.findByIban(iban).orElseThrow(AccountRequestedNotFoundException::new);
    ResponseEntity<ExchangeRatesApiResponse> response =
        exchangeRatesApiClient.getExchangeRate(
            exchangeRateApiKey,
            account.getCurrency().getCurrencyCode(),
            currency.getCurrencyCode());

    if (response.getStatusCode().equals(HttpStatus.OK)) {
      Float rate =
          Optional.ofNullable(response.getBody())
              .flatMap(body -> body.getRate(currency.getCurrencyCode()))
              .orElseThrow(
                  () ->
                      new ExchangeRateNotAvailableForCurrenciesException(
                          account.getCurrency(), currency));

      return new Balance(
          account
              .getBalance()
              .getAmount()
              .multiply(BigDecimal.valueOf(rate), new MathContext(2, RoundingMode.CEILING)));
    } else {
      throw new ExchangeRateNotAvailableForCurrenciesException(account.getCurrency(), currency);
    }
  }
}
