//package pt.galina.spring_webflux_demo.converter;
//
//import org.springframework.stereotype.Component;
//import pt.galina.spring_webflux_demo.dto.IngredientDTO;
//import pt.galina.spring_webflux_demo.entity.taco.Ingredient;
//
//@Component
//public class IngredientDtoConverter {
//
//    public IngredientDTO toDto(Ingredient ingredient) {
//        IngredientDTO dto = new IngredientDTO();
//        dto.setId(ingredient.getId());
//        dto.setName(ingredient.getName());
//        dto.setType(ingredient.getType().toString());
//        return dto;
//    }
//
//    public Ingredient toEntity(IngredientDTO dto) {
//        return new Ingredient(dto.getId(), dto.getName(), Ingredient.Type.valueOf(dto.getType()));
//    }
//}
