package test.ervinracz.accounts.accounts.infrastructure.errorhandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
