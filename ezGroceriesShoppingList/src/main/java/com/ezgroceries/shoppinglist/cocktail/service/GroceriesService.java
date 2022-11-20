package com.ezgroceries.shoppinglist.cocktail.service;

import com.ezgroceries.shoppinglist.cocktail.model.CocktailResource;
import com.ezgroceries.shoppinglist.cocktail.model.ShoppingListResource;
import java.util.List;

public interface GroceriesService {
    public List<CocktailResource> getCocktailByName(String cocktailName);
    public List<CocktailResource> getCocktailById(String cocktailId);
    public ShoppingListResource createShoppingList(String shoppingListName);
    public ShoppingListResource addCocktailToShoppingList(String shoppingListId, String cocktailId);
    public List<ShoppingListResource> getShoppingListById(String shoppingListId);

}
