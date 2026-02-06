package com.devjonas.inventory.infra.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tools.jackson.databind.exc.InvalidFormatException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    final private MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource message) {
        this.messageSource = message;
    }

    @ExceptionHandler
    public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentsNotValidException(MethodArgumentNotValidException ex) {
        List<ErrorMessageDTO> errorMessages = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(err -> {
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            ErrorMessageDTO error = new ErrorMessageDTO(message, err.getField());
            errorMessages.add(error);
        });

        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessageDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String message = "Invalid data in the request";
        String field = "body";

        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException ifx = (InvalidFormatException) ex.getCause();

            if (ifx.getTargetType() != null && ifx.getTargetType().isEnum()) {
                field = ifx.getPath().isEmpty() ? "unknown" : ifx.getPath().get(0).getPropertyName();
                Object invalidValue = ifx.getValue();

                String validValues = Arrays.stream(ifx.getTargetType().getEnumConstants())
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));

                message = String.format(
                        "The value '%s' is not valid for the field '%s'. Accepted values: [%s]",
                        invalidValue, field, validValues
                );
            } else {
                // Outros erros de formato (ex: String para Integer)
                field = ifx.getPath().isEmpty() ? "unknown" : ifx.getPath().get(0).getPropertyName();
                message = String.format(
                        "Invalid format for the field '%s'. Expected: %s",
                        field, ifx.getTargetType().getSimpleName()
                );
            }
        }

        ErrorMessageDTO error = new ErrorMessageDTO(message, field);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
