package com.ezgroceries.shoppinglist.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.ezgroceries.shoppinglist.cocktail.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.jpa.cocktail.CocktailEntity;
import com.ezgroceries.shoppinglist.jpa.cocktail.CocktailRepository;
import java.util.HashSet;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class CocktailServiceImplTest {

    @Autowired
    private ShoppingListService shoppingListService;

    @Autowired
    private CocktailServiceImpl cocktailService;

    @Autowired
    private CocktailRepository cocktailRepository;

    @Test
    void update() {
        ShoppingListResource shoppingListResource = shoppingListService.create("Ragu");
        CocktailEntity cocktailEntity = cocktailRepository.save(new CocktailEntity("drinkid", "name", new HashSet<String>(List.of("test"))));
        ShoppingListResource output = cocktailService.update(shoppingListResource.getShoppingListId(), cocktailEntity.getEntityId().toString());
        assertThat(output.getName()).isEqualTo("Ragu");
        assertThat(output.getShoppingListId()).isEqualTo(shoppingListResource.getShoppingListId());
    }

}