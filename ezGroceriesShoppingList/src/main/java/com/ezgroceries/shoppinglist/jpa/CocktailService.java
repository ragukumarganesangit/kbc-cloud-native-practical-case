package com.ezgroceries.shoppinglist.jpa;

import com.ezgroceries.shoppinglist.cocktail.model.CocktailResource;
import com.ezgroceries.shoppinglist.cocktail.model.ShoppingListResource;
import java.util.List;

public interface CocktailService {
    public ShoppingListResource update(String shoppingListId, String cocktailId);
}
