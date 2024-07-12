package pt.galina.chap4mongodb.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pt.galina.chap4mongodb.data.IngredientRepository;
import pt.galina.chap4mongodb.entity.Ingredient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

class IngredientByIdConverterTest {

    @Mock
    private IngredientRepository ingredientRepository;

    private IngredientByIdConverter converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        converter = new IngredientByIdConverter(ingredientRepository);
    }

    @Test
    void convert_ShouldReturnIngredient_WhenIdExists() {
        // Arrange
        String id = "FLTO";
        Ingredient ingredient = new Ingredient(id, "Flour Tortilla", Ingredient.Type.WRAP);
        given(ingredientRepository.findById(id)).willReturn(Optional.of(ingredient));

        // Act
        Ingredient result = converter.convert(id);

        // Assert
        assertEquals(ingredient, result);
    }

    @Test
    void convert_ShouldReturnNull_WhenIdDoesNotExist() {
        // Arrange
        String id = "NON_EXISTENT_ID";
        given(ingredientRepository.findById(id)).willReturn(Optional.empty());

        // Act
        Ingredient result = converter.convert(id);

        // Assert
        assertNull(result);
    }
}
