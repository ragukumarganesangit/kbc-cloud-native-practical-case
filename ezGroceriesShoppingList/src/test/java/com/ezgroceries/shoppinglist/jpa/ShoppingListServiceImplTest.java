package com.ezgroceries.shoppinglist.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.ezgroceries.shoppinglist.cocktail.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.jpa.shoppinglist.ShoppingListRepository;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class ShoppingListServiceImplTest {

    @Autowired
    private ShoppingListService shoppingListService;

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Test
    void createShoppingListTest() {
        ShoppingListResource shoppingListResource = shoppingListService.create("Ragu");
        assertThat(shoppingListResource.getName()).isEqualTo("Ragu");
        //getting it from database
        assertThat(shoppingListRepository.findById(UUID.fromString(shoppingListResource.getShoppingListId())).get().getName()).isEqualTo("Ragu");
    }

    @Test
    void getShoppingListByIdTest() {
        ShoppingListResource shoppingListResource = shoppingListService.create("Ragu");
        ShoppingListResource shoppingListById = shoppingListService.getShoppingListById(shoppingListResource.getShoppingListId());
        assertThat(shoppingListById.getName()).isEqualTo("Ragu");
    }

    @Test
    void getShoppingListsTest() {
        shoppingListService.create("Ragu");
        shoppingListService.create("Ramya");
        shoppingListService.create("Stefen");
        List<ShoppingListResource> shoppingLists = shoppingListService.getShoppingLists();
        assertThat(shoppingLists.size()).isEqualTo(3);
    }

}