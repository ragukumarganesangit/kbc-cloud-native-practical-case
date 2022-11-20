package com.ezgroceries.shoppinglist.jpa;

import com.ezgroceries.shoppinglist.cocktail.model.ShoppingListResource;
import java.util.List;

public interface ShoppingListService {
    public ShoppingListResource create(String shoppingListName);
    public ShoppingListResource getShoppingListById(String shoppingListId);
    public List<ShoppingListResource> getShoppingLists();

}
