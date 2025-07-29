package com.personal.productservice.controlleradvice;

import com.personal.productservice.dto.ErrorResponseDTO;
import com.personal.productservice.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponseDTO> handleNullPointerException(){
        ErrorResponseDTO errorResponseDTO =
                new ErrorResponseDTO("NPE exception buddy!");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDTO);
    }

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<ErrorResponseDTO> handleArithmeticResponse(){
        ErrorResponseDTO errorResponseDTO =
                new ErrorResponseDTO("Arithmetic exception buddy!");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDTO);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleProductNotFoundResponse(ProductNotFoundException productNotFoundException){
        ErrorResponseDTO errorResponseDTO =
                new ErrorResponseDTO(productNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDTO);
    }
}
