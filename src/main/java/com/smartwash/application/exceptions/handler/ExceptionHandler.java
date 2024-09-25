package com.smartwash.application.exceptions.handler;

import com.smartwash.application.exceptions.customs.BadRequestException;
import com.smartwash.application.exceptions.customs.NotFoundException;
import com.smartwash.application.exceptions.models.PadraoErro;
import com.smartwash.application.exceptions.models.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<PadraoErro> NotFoundException(NotFoundException exception, HttpServletRequest request){
        return erroPadronizado(HttpStatus.NOT_FOUND, "Nenhum Objeto retornado!", exception, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    public ResponseEntity<PadraoErro> BadRequestException(BadRequestException exception, HttpServletRequest request){
        return erroPadronizado(HttpStatus.BAD_REQUEST, "Nenhum resultado retornado!", exception, request);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PadraoErro> validationException(MethodArgumentNotValidException error, HttpServletRequest request) {

        ValidationError validationError = new ValidationError(System.currentTimeMillis(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de Validação de Campos!",
                error.getMessage(), request.getRequestURI());

        error.getBindingResult().getFieldErrors().forEach(fieldError -> validationError.addError(
                fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validationError);
    }

    private ResponseEntity<PadraoErro> erroPadronizado(HttpStatus httpStatus, String mensagemGenericaErro,
                                                       Exception exception, HttpServletRequest request) {

        return ResponseEntity.status(httpStatus).body(new PadraoErro(System.currentTimeMillis(), httpStatus.value(),
                mensagemGenericaErro, exception.getMessage(), request.getRequestURI()));
    }
}
