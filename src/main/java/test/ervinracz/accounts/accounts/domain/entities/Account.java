package test.ervinracz.accounts.accounts.domain.entities;

import java.time.LocalDateTime;
import java.util.Currency;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import test.ervinracz.accounts.accounts.domain.types.Balance;
import test.ervinracz.accounts.accounts.domain.types.BalanceConverter;
import test.ervinracz.accounts.accounts.domain.types.Iban;
import test.ervinracz.accounts.accounts.domain.types.IbanConverter;

@Getter
@Builder
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Account {

  @Convert(converter = IbanConverter.class)
  @Column(nullable = false, unique = true, updatable = false)
  Iban iban;

  @Column(nullable = false)
  Currency currency;

  @Convert(converter = BalanceConverter.class)
  @Column(nullable = false)
  Balance balance;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  LocalDateTime createdDate;

  @LastModifiedDate
  @Column(nullable = false)
  LocalDateTime lastModifiedDate;
}
