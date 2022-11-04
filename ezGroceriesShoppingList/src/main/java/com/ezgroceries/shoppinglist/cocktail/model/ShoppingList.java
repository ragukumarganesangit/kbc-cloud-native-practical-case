package com.ezgroceries.shoppinglist.cocktail.model;

import java.util.List;
import lombok.Data;

@Data
public class ShoppingList {

    private String shoppingListId;
    private String name;
    private List<Cocktail> cocktail;

    public ShoppingList(String shoppingListId, String name, List<Cocktail> cocktail) {
        this.shoppingListId = shoppingListId;
        this.name = name;
        this.cocktail = cocktail;
    }
}
