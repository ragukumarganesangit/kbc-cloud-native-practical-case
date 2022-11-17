package com.ezgroceries.shoppinglist.cocktail.service;

import com.ezgroceries.shoppinglist.cocktail.model.Cocktail;
import com.ezgroceries.shoppinglist.cocktail.model.ShoppingList;
import com.ezgroceries.shoppinglist.feignclient.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.feignclient.model.CocktailDBResponse;
import com.ezgroceries.shoppinglist.feignclient.model.CocktailDBResponse.DrinkResource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GroceriesServiceImpl implements GroceriesService {

    private CocktailDBClient dbClient;

    public GroceriesServiceImpl(CocktailDBClient dbClient) {
        this.dbClient = dbClient;
    }

    private static final Logger log = LoggerFactory.getLogger(GroceriesServiceImpl.class);


    @Override
    public List<Cocktail> getCocktailByName(String cocktailName) {
        CocktailDBResponse cocktailDBResponse = dbClient.searchCocktails(cocktailName);
        List<Cocktail> cocktailList = fillCocktailWithActualValue(cocktailDBResponse);
        if (cocktailName == null || cocktailName.isEmpty()) {
            return cocktailList;
        }
        return cocktailList.stream().filter(c -> c.getName().toLowerCase().contains(cocktailName.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public List<Cocktail> getCocktailById(String cocktailId) {
        List<Cocktail> cocktailList = fillCocktail();
        if (cocktailId == null || cocktailId.isEmpty()) {
            return cocktailList;
        }
        return cocktailList.stream().filter(c -> c.getId().equals(cocktailId)).collect(Collectors.toList());
    }

    @Override
    public ShoppingList createShoppingList(String shoppingListName) {
        /**
         * This is just dummy code.It's not saving any code.Focusing only on contract
         */
        log.info("Shopping list created with the name {}", shoppingListName);
        return new ShoppingList("90689338-499a-4c49-af90-f1e73068ad4f", shoppingListName, null);
    }

    @Override
    public ShoppingList addCocktailToShoppingList(String shoppingListId, String cocktailId) {
        return new ShoppingList(shoppingListId, "Stephanie's birthday", getCocktailById(cocktailId));
    }

    @Override
    public List<ShoppingList> getShoppingListById(String shoppingListId) {
        List<ShoppingList> shoppingList = fillShoppingList();
        if (shoppingList == null || shoppingList.isEmpty()) {
            return shoppingList;
        }
        return shoppingList.stream().filter(c -> c.getShoppingListId().equals(shoppingListId)).collect(Collectors.toList());
    }

    private List<Cocktail> fillCocktail() {
        Cocktail margerita = new Cocktail("23b3d85a-3928-41c0-a533-6538a71e17c4", "Margerita", "Cocktail glass",
                "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..",
                "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg", List.of("Tequila", "Triple sec", "Lime juice", "salt"));
        Cocktail blueMargerita = new Cocktail("d615ec78-fe93-467b-8d26-5d26d8eab073", "Blue Margerita", "Cocktail glass",
                "Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..",
                "https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg", List.of("Tequila", "Blue Curacao", "Lime juice", "salt"));
        List<Cocktail> cocktailList = new ArrayList<>();
        cocktailList.add(margerita);
        cocktailList.add(blueMargerita);
        return cocktailList;
    }

    private List<Cocktail> fillCocktailWithActualValue(CocktailDBResponse cocktailDBResponse) {
        return cocktailDBResponse.getDrinks().stream().map(this::mapCocktailObject).collect(Collectors.toList());
    }

    private Cocktail mapCocktailObject(DrinkResource drinkResource) {
        return  new Cocktail(drinkResource.getIdDrink(), drinkResource.getStrDrink(), drinkResource.getStrGlass(),
                drinkResource.getStrInstructions(), drinkResource.getStrDrinkThumb(), List.of(drinkResource.getStrIngredient1() != null ? drinkResource.getStrIngredient1():" ",
                drinkResource.getStrIngredient2() != null ? drinkResource.getStrIngredient2():" ", drinkResource.getStrIngredient3() != null ? drinkResource.getStrIngredient3():" "));
    }

    private List<ShoppingList> fillShoppingList() {
        List<ShoppingList> shoppingLists = new ArrayList<>();
        ShoppingList shoppingList1 = new ShoppingList("90689338-499a-4c49-af90-f1e73068ad4f", "Stephanie's birthday", fillCocktail());
        ShoppingList shoppingList2 = new ShoppingList("6c7d09c2-8a25-4d54-a979-25ae779d2465", "My birthday", fillCocktail());
        shoppingLists.add(shoppingList1);
        shoppingLists.add(shoppingList2);
        return shoppingLists;
    }
}
