package test.ervinracz.accounts.accounts.domain.exceptions;

import lombok.Getter;

@Getter
public class AccountRequestedNotFoundException extends RuntimeException {
  public final String message;

  public AccountRequestedNotFoundException() {
    super("Account Requested Not Found");
    this.message = this.getMessage();
  }

  public AccountRequestedNotFoundException(final String message) {
    super(message);
    this.message = message;
  }
}
