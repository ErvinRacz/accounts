package test.ervinracz.accounts.accounts.domain.types;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BalanceTest {

  @Test
  void should_deposit_positive_or_zero_amount() {
    var balance = new Balance();
    assertThat(balance.getAmount(), is(BigDecimal.ZERO));
  }

  @Test
  void should_not_allow_to_deposit_negative_value() {
    assertThrows(IllegalArgumentException.class, () -> new Balance(BigDecimal.valueOf(-1000)));
  }
}
