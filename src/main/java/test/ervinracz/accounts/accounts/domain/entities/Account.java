package test.ervinracz.accounts.accounts.domain.entities;

import java.util.Currency;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import test.ervinracz.accounts.accounts.domain.types.Balance;
import test.ervinracz.accounts.accounts.domain.types.BalanceConverter;
import test.ervinracz.accounts.accounts.domain.types.Iban;
import test.ervinracz.accounts.accounts.domain.types.IbanConverter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Account {

  @Convert(converter = IbanConverter.class)
  @Column(nullable = false, unique = true)
  Iban iban;

  @Column(nullable = false)
  Currency currency;

  @Convert(converter = BalanceConverter.class)
  @Column(nullable = false)
  Balance balance;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Override
  public String toString() {
    return "Account{"
        + "iban="
        + String.format("%s...", iban.getObfuscatedIban())
        + ", currency="
        + currency
        + ", balance="
        + balance
        + ", id="
        + id
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Account account = (Account) o;
    return id != null && Objects.equals(id, account.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
