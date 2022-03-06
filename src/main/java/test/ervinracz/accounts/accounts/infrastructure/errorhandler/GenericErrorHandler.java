package test.ervinracz.accounts.accounts.infrastructure.errorhandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import test.ervinracz.accounts.accounts.domain.exceptions.AccountRequestedNotFoundException;
import test.ervinracz.accounts.accounts.domain.exceptions.ExchangeRateNotAvailableForCurrenciesException;

@Slf4j
@RestControllerAdvice
public class GenericErrorHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<SimpleMessage> handleEntityNotFoundException(
      EntityNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new SimpleMessage(exception.getMessage()));
  }

  @ExceptionHandler(EntityExistsException.class)
  public ResponseEntity<SimpleMessage> handleEntityExistsException(
      EntityExistsException exception) {
    return ResponseEntity.badRequest().body(new SimpleMessage(exception.getMessage()));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<SimpleMessage> handleDataIntegrityViolationException(
      DataIntegrityViolationException exception) {
    return ResponseEntity.badRequest().body(new SimpleMessage(exception.getMessage()));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<SimpleMessage> handleConstraintViolationException(
      ConstraintViolationException exception) {
    return ResponseEntity.badRequest().body(new SimpleMessage(exception.getMessage()));
  }

  @ExceptionHandler(AccountRequestedNotFoundException.class)
  public ResponseEntity<SimpleMessage> handleAccountRequestedNotFoundException(
      AccountRequestedNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new SimpleMessage(exception.getMessage()));
  }

  @ExceptionHandler(ExchangeRateNotAvailableForCurrenciesException.class)
  public ResponseEntity<SimpleMessage> handleExchangeRateNotAvailableException(
      ExchangeRateNotAvailableForCurrenciesException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new SimpleMessage(exception.getMessage()));
  }
}
