package test.ervinracz.accounts.accounts.application;

import java.util.Currency;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import test.ervinracz.accounts.accounts.domain.AccountService;
import test.ervinracz.accounts.accounts.domain.dtos.AccountDto;
import test.ervinracz.accounts.accounts.domain.types.Balance;
import test.ervinracz.accounts.accounts.domain.types.Iban;
import test.ervinracz.accounts.accounts.infrastructure.annotations.CurrencyValidation;
import test.ervinracz.accounts.accounts.infrastructure.annotations.IbanValidation;

@Validated
@RequiredArgsConstructor
@RestController("accounts")
@RequestMapping("accounts")
public class AccountController {

  private final AccountService accountService;

  @PostMapping
  ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto request) {
    return ResponseEntity.ok(accountService.createAccount(request));
  }

  @GetMapping
  ResponseEntity<List<AccountDto>> getAccounts() {
    return ResponseEntity.ok(accountService.getAllAccounts());
  }

  @GetMapping("/{iban}/getBalanceInCurrency")
  public ResponseEntity<Balance> getBalanceInCurrency(
      @PathVariable @IbanValidation String iban,
      @RequestParam @CurrencyValidation String currency) {
    return ResponseEntity.ok(
        accountService.getBalanceInCurrency(Iban.of(iban), Currency.getInstance(currency)));
  }
}
