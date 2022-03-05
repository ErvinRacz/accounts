package test.ervinracz.accounts.accounts.domain.dtos;

import java.math.BigDecimal;
import java.util.Currency;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import test.ervinracz.accounts.accounts.domain.entities.Account;
import test.ervinracz.accounts.accounts.domain.types.Balance;
import test.ervinracz.accounts.accounts.domain.types.Iban;
import test.ervinracz.accounts.accounts.infrastructure.annotations.CurrencyValidation;
import test.ervinracz.accounts.accounts.infrastructure.annotations.IbanValidation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

  Long id;
  @NotNull @IbanValidation String iban;
  @NotEmpty @NotNull @CurrencyValidation String currency;

  @NotNull
  @DecimalMin(value = "0.0")
  @Digits(integer = 15, fraction = 2)
  BigDecimal balance;

  public static AccountDto from(Account entity) {
    return AccountDto.builder()
        .id(entity.getId())
        .iban(entity.getIban().getIbanAsString())
        .currency(entity.getCurrency().getCurrencyCode())
        .balance(entity.getBalance().getAmount())
        .build();
  }

  public Account toEntity() {
    return Account.builder()
        .id(id)
        .iban(Iban.of(iban))
        .currency(Currency.getInstance(currency))
        .balance(new Balance(balance))
        .build();
  }
}
