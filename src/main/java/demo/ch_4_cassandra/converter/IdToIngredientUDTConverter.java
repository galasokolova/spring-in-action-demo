//package demo.ch_4_cassandra.converter;
//
//import demo.ch_4_cassandra.data.IngredientRepository;
//import demo.ch_4_cassandra.entity.IngredientUDT;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.lang.NonNull;
//import org.springframework.stereotype.Component;
//
//@Component
//public class IdToIngredientUDTConverter implements Converter<String, IngredientUDT> {
//
//    private final IngredientRepository ingredientRepository;
//
//    public IdToIngredientUDTConverter(IngredientRepository ingredientRepository) {
//        this.ingredientRepository = ingredientRepository;
//    }
//
//    @Override
//    public IngredientUDT convert(@NonNull String id) {
//        return ingredientRepository.findById(id)
//                .map(i -> new IngredientUDT(i.getName(), i.getType()))
//                .orElse(null);
//    }
//}
