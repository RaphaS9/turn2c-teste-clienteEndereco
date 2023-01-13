package br.com.rsousa.turn2c.api.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadorDeExcecoes {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity voltaErro404(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO(exception));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity voltaErro400(MethodArgumentNotValidException exception) {
        List<FieldError> erros = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(ExceptionValidationDTO:: new));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity voltaErro(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(new ExceptionDTO(exception));
    }


    private record ExceptionValidationDTO(String campo, String mensagem) {
        public ExceptionValidationDTO(FieldError exception) {
            this(exception.getField(), exception.getDefaultMessage());
        }
    }

    private record ExceptionDTO(String mensagem) {
        public ExceptionDTO(Exception exception) {
            this(exception.getLocalizedMessage());
        }
    }
}
