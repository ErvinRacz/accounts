package test.ervinracz.accounts.accounts.application;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import test.ervinracz.accounts.accounts.domain.AccountService;
import test.ervinracz.accounts.accounts.domain.dtos.AccountDto;

@Validated
@RequiredArgsConstructor
@RestController("/accounts")
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
}
