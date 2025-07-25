package ccpetrov01.userapplication.Exceptions;

import jakarta.validation.UnexpectedTypeException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex){
        String errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));

        return ResponseEntity.badRequest().body("Validation Failed!" + errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidJSON(HttpMessageNotReadableException ex){
        return ResponseEntity.badRequest().body("malformed JSON " + ex.getMostSpecificCause().getMessage());

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrity (DataIntegrityViolationException ex){
        return ResponseEntity.badRequest().body("Database constraint violation:" + ex.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalException(IllegalStateException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoFound(NoSuchElementException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found!" + ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleNotFound(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ClassNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data type is wrong" + ex.getMessage());
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<String> handleNotFound(UnexpectedTypeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Constraints error" + ex.getMessage());
    }


}

