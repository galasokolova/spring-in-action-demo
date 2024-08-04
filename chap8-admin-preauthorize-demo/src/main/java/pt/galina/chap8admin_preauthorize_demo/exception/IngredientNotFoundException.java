package pt.galina.chap8admin_preauthorize_demo.exception;

public class IngredientNotFoundException extends RuntimeException{
    public IngredientNotFoundException(String id){
        super("Ingredient with id " + id + " not found");
    }
}
