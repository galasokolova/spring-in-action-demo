package pt.galina.chap8admin_preauthorize_demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pt.galina.chap8admin_preauthorize_demo.exception.IngredientNotFoundException;

@RestControllerAdvice
public class IngredientControllerAdvice {

    @ExceptionHandler(IngredientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String ingredientNotFoundHandler(IngredientNotFoundException ex) {
        return ex.getMessage();
    }
}
