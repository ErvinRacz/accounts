package test.ervinracz.accounts.accounts.domain.types;

import lombok.Value;
import org.apache.commons.lang3.StringUtils;
import test.ervinracz.accounts.accounts.utils.IbanUtils;

@Value
public class Iban {

  String iban;

  private Iban(String iban) {
    this.iban = iban;
  }

  public static Iban of(String iban) {
    iban = StringUtils.deleteWhitespace(iban);

    if (!IbanUtils.isValid(iban)) {
      throw new IllegalArgumentException(
          String.format(
              "Following string is not an IBAN: %s", iban.trim().substring(0, 4) + "..."));
    }

    return new Iban(iban);
  }

  public String getObfuscatedIban() {
    return String.format("%s...", iban.substring(0, 5));
  }

  @Override
  public String toString() {
    return getObfuscatedIban();
  }

  public String getIbanAsString() {
    return iban;
  }
}
