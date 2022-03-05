package test.ervinracz.accounts.accounts.domain.types;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class IbanConverter implements AttributeConverter<Iban, String> {

  @Override
  public String convertToDatabaseColumn(Iban attribute) {
    if (attribute == null) {
      return null;
    }

    return attribute.getIbanAsString();
  }

  @Override
  public Iban convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    return Iban.of(dbData);
  }
}
