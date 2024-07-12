package pt.galina.chap4cassandra.converter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pt.galina.chap4cassandra.data.IngredientRepository;
import pt.galina.chap4cassandra.entity.Ingredient;
import pt.galina.chap4cassandra.entity.IngredientUDT;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class IdToIngredientUDTConverterTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IdToIngredientUDTConverter converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void convert_ShouldReturnIngredientUDT_WhenIngredientExists() {
        // Arrange
        Ingredient ingredient = new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP);
        when(ingredientRepository.findById(anyString())).thenReturn(Optional.of(ingredient));

        // Act
        IngredientUDT result = converter.convert("FLTO");

        // Assert
        assert result != null;
        assertEquals("Flour Tortilla", result.getName());
        assertEquals(Ingredient.Type.WRAP, result.getType());
    }

    @Test
    void convert_ShouldReturnNull_WhenIngredientDoesNotExist() {
        // Arrange
        when(ingredientRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        IngredientUDT result = converter.convert("INVALID_ID");

        // Assert
        assertNull(result);
    }
}
