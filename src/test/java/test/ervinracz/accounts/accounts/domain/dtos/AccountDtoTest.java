package test.ervinracz.accounts.accounts.domain.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Currency;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import test.ervinracz.accounts.accounts.domain.entities.Account;
import test.ervinracz.accounts.accounts.domain.types.Balance;
import test.ervinracz.accounts.accounts.domain.types.Iban;

class AccountDtoTest {

  @Test
  public void should_convert_dto_into_entity() {
    // Arrange
    AccountDto dto =
        AccountDto.builder()
            .balance(BigDecimal.ZERO)
            .currency("RON")
            .iban("AL47 2121 1009 0000 0002 3569 8741")
            .build();
    // Action
    Account account = dto.toEntity();
    // Assert
    assertEquals(StringUtils.deleteWhitespace(dto.iban), account.getIban().getIbanAsString());
    assertEquals(dto.balance, account.getBalance().getAmount());
    assertEquals(dto.currency, account.getCurrency().getCurrencyCode());
  }

  @Test
  public void should_convert_entity_into_dto() {
    // Arrange
    Account account =
        Account.builder()
            .balance(new Balance(0))
            .currency(Currency.getInstance("RON"))
            .iban(Iban.of("AL47 2121 1009 0000 0002 3569 8741"))
            .build();
    // Action
    AccountDto dto = AccountDto.from(account);
    // Assert
    assertEquals(StringUtils.deleteWhitespace(dto.iban), account.getIban().getIbanAsString());
    assertEquals(dto.balance, account.getBalance().getAmount());
    assertEquals(dto.currency, account.getCurrency().getCurrencyCode());
  }
}
