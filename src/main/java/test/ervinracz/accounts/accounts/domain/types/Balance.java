package test.ervinracz.accounts.accounts.domain.types;

import java.math.BigDecimal;
import lombok.Value;

@Value
public class Balance {

  BigDecimal amount;

  public Balance() {
    amount = BigDecimal.ZERO;
  }

  public Balance(BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) >= 0) {
      this.amount = amount;
    } else {
      throw new IllegalArgumentException("The amount to deposit must be greater than zero");
    }
  }

  public Balance(long amount) {
    this(BigDecimal.valueOf(amount));
  }

  public Balance(String amount) {
    this(new BigDecimal(amount));
  }
}
