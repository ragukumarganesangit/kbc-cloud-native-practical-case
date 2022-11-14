package com.ezgroceries.shoppinglist.cocktail.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ezgroceries.shoppinglist.cocktail.model.Cocktail;
import com.ezgroceries.shoppinglist.cocktail.model.ShoppingList;
import com.ezgroceries.shoppinglist.feignclient.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.feignclient.model.CocktailDBResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;

@SpringBootTest
class GroceriesServiceImplTest {

    @Autowired
    private GroceriesService groceriesService;

    @MockBean
    private CocktailDBClient cocktailDBClient;

    @Autowired
    private CocktailDBResponse cocktailDBResponse;


    @Test
    void getCocktailNamefound() {
        when(cocktailDBClient.searchCocktails(anyString())).thenReturn(cocktailDBResponse);

        List<Cocktail> cocktailList = groceriesService.getCocktailByName("Margerita");
        assertThat(cocktailList).isNotNull();
        assertThat(cocktailList.size()).isEqualTo(1);
        assertThat(cocktailList.get(0).getName()).isEqualTo("Margerita");
    }

    @Test
    void getCocktailNameNotfound() {
        when(cocktailDBClient.searchCocktails(anyString())).thenReturn(cocktailDBResponse);
        List<Cocktail> cocktailList = groceriesService.getCocktailByName("test");
        assertThat(cocktailList.size()).isEqualTo(0);
    }

    @Test
    void getCocktailIdfound() {
        List<Cocktail> cocktailList = groceriesService.getCocktailById("23b3d85a-3928-41c0-a533-6538a71e17c4");
        assertThat(cocktailList).isNotNull();
        assertThat(cocktailList.size()).isEqualTo(1);
        assertThat(cocktailList.get(0).getId()).isEqualTo("23b3d85a-3928-41c0-a533-6538a71e17c4");
    }

    @Test
    void getCocktailIdNotfound() {
        List<Cocktail> cocktailList = groceriesService.getCocktailById("42dsdsdsd-3928-dsds-a533-6538a71e17c4");
        assertThat(cocktailList.size()).isEqualTo(0);
    }

    @Test
    void createShoppingList() {
        ShoppingList shoppingList = groceriesService.createShoppingList("test");
        assertThat(shoppingList).isNotNull();
        assertThat(shoppingList.getName()).isEqualTo("test");
    }

    @Test
    void addCocktailToShoppingList() {
        ShoppingList shoppingList = groceriesService.addCocktailToShoppingList("test", "23b3d85a-3928-41c0-a533-6538a71e17c4");
        assertThat(shoppingList).isNotNull();
        assertThat(shoppingList.getShoppingListId()).isEqualTo("test");
        assertThat(shoppingList.getCocktail().get(0).getId()).isEqualTo("23b3d85a-3928-41c0-a533-6538a71e17c4");
    }

    @Test
    void getShoppingListById() {
        List<ShoppingList> shoppingList = groceriesService.getShoppingListById("90689338-499a-4c49-af90-f1e73068ad4f");
        assertThat(shoppingList.size()).isEqualTo(1);
        assertThat(shoppingList.get(0).getShoppingListId()).isEqualTo("90689338-499a-4c49-af90-f1e73068ad4f");
    }
}