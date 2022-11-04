package com.ezgroceries.shoppinglist.cocktail.service;

import com.ezgroceries.shoppinglist.cocktail.model.Cocktail;
import com.ezgroceries.shoppinglist.cocktail.model.ShoppingList;
import java.util.List;

public interface GroceriesService {
    public List<Cocktail> getCocktailByName(String cocktailName);
    public List<Cocktail> getCocktailById(String cocktailId);
    public ShoppingList createShoppingList(String shoppingListName);
    public ShoppingList addCocktailToShoppingList(String shoppingListId, String cocktailId);
    public List<ShoppingList> getShoppingListById(String shoppingListId);

}
