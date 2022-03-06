package test.ervinracz.accounts.accounts.domain.exceptions;

public class AccountRequestedNotFoundException extends RuntimeException {

  public AccountRequestedNotFoundException() {
    super("Account Requested Not Found");
  }

  public AccountRequestedNotFoundException(final String message) {
    super(message);
  }
}
