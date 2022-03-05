package test.ervinracz.accounts.accounts.domain.types;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BalanceTest {

  @Test
  void should_deposit_positive_value() {
    var balance = new Balance();
    balance.deposit(BigDecimal.valueOf(1000));
    assertThat(balance.getAmount(), is(BigDecimal.valueOf(1000)));
  }

  @Test
  void should_not_allow_to_deposit_negative_value() {
    var balance = new Balance();
    assertThrows(
        UnsupportedOperationException.class, () -> balance.deposit(BigDecimal.valueOf(-1000)));
  }
}
