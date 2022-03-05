package test.ervinracz.accounts.accounts.domain.types;

import java.math.BigDecimal;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BalanceConverter implements AttributeConverter<Balance, BigDecimal> {

  @Override
  public BigDecimal convertToDatabaseColumn(Balance attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.getAmount();
  }

  @Override
  public Balance convertToEntityAttribute(BigDecimal dbData) {
    if (dbData == null) {
      return null;
    }
    return new Balance(dbData);
  }
}
