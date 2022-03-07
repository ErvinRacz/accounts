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
          String.format("Following string is not an IBAN: %s", IbanUtils.obfuscate(iban)));
    }

    return new Iban(iban);
  }

  public String getObfuscatedIban() {
    return IbanUtils.obfuscate(iban);
  }

  @Override
  public String toString() {
    return getObfuscatedIban();
  }

  public String getIbanAsString() {
    return iban;
  }
}
