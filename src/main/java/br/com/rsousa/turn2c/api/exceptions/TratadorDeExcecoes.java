package br.com.rsousa.turn2c.api.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadorDeExcecoes {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity voltaErro404(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionEntityDTO(exception));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity voltaErro400(MethodArgumentNotValidException exception) {
        List<FieldError> erros = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(ExceptionValidationDTO:: new));
    }


    private record ExceptionValidationDTO(String campo, String mensagem) {
        public ExceptionValidationDTO(FieldError exception) {
            this(exception.getField(), exception.getDefaultMessage());
        }
    }

    private record ExceptionEntityDTO(String mensagem) {
        public ExceptionEntityDTO(EntityNotFoundException exception) {
            this(exception.getLocalizedMessage());
        }
    }
}
