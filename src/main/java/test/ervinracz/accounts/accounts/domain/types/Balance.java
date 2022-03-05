package test.ervinracz.accounts.accounts.domain.types;

import java.math.BigDecimal;

public class Balance {

  private BigDecimal amount;

  public Balance() {
    amount = BigDecimal.ZERO;
  }

  public Balance(BigDecimal amount) {
    this.amount = amount;
  }

  public void deposit(BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) > 0) {
      this.amount = this.amount.add(amount);
    } else {
      throw new UnsupportedOperationException("The amount to deposit must be greater than zero");
    }
  }

  public void withdraw(BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) > 0) {
      this.amount = this.amount.subtract(amount);
    } else {
      throw new UnsupportedOperationException("The amount to withdraw must be greater than zero");
    }
  }

  public BigDecimal getAmount() {
    return amount;
  }
}
