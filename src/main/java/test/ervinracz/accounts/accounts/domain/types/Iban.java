package test.ervinracz.accounts.accounts.domain.types;

import java.math.BigInteger;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value
public class Iban {

  public static final int IBAN_MIN_SIZE = 15; // Can depend on country, so TODO: introduce an enum
  public static final int IBAN_MAX_SIZE = 34; // Can depend on country, so TODO: introduce an enum

  String iban;

  private Iban(String iban) {
    this.iban = iban;
  }

  public static Iban of(String iban) {
    iban = StringUtils.deleteWhitespace(iban);

    if (!isIbanStringValid(iban)) {
      throw new IllegalArgumentException(String.format("Following string is not an IBAN: %s",
          iban.trim().substring(0, 4) + "..."));
    }

    return new Iban(iban);
  }

  /**
   * Validation according to https://en.wikipedia.org/wiki/International_Bank_Account_Number#Validating_the_IBAN
   *
   * @param iban string that is supposedly an IBAN
   * @return true if input string is a valid IBAN
   */
  private static boolean isIbanStringValid(final String iban) {
    var whiteSpaceLess = StringUtils.deleteWhitespace(iban);
    if (whiteSpaceLess.length() < IBAN_MIN_SIZE || whiteSpaceLess.length() > IBAN_MAX_SIZE) {
      return false;
    }

    var withLettersAtTheEnd = whiteSpaceLess.substring(4) + whiteSpaceLess.substring(0, 4);
    StringBuilder total = new StringBuilder();

    for (char letter : withLettersAtTheEnd.toCharArray()) {
      int charValue = Character.getNumericValue(letter);
      if (charValue < 0 || charValue > 35) {
        return false;
      }
      total.append(charValue);
    }

    BigInteger totalInt = new BigInteger(total.toString());
    return totalInt.mod(new BigInteger("97")).equals(BigInteger.ONE);
  }

  public String getObfuscatedIban() {
    return String.format("%s...", iban.substring(0, 5));
  }

  public String getIbanAsString() {
    return iban;
  }
}
