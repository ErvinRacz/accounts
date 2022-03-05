package test.ervinracz.accounts.accounts.domain.types;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class IbanTest {

  @ParameterizedTest
  @ValueSource(
      strings = {
        "AL47 2121 1009 0000 0002 3569 8741",
        "CY 17 002 00128 0000001200527600",
        "KW81CBKU0000000000001234560101",
        "LU 28 001 9400644750000",
        "NO 93 8601 1117947"
      })
  void should_produce_valid_iban(String inputIban) {
    assertThat(Iban.of(inputIban).getIbanAsString(), matchesPattern("^.{15,34}$"));
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "GB94BARC20201530093459",
        "GB96BARC20201530",
        "GB68CITI18500483515538SDWESAD435DWAD",
        "GB24BARC2e0201630093459",
        "GB00HALF110%%16111455365"
      })
  void should_not_allow_iban_creation(String inputIban) {
    assertThrows(IllegalArgumentException.class, () -> Iban.of(inputIban));
  }
}
