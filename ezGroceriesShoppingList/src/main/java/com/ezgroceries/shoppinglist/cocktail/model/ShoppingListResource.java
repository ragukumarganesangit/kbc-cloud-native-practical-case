package com.ezgroceries.shoppinglist.cocktail.model;

import java.util.List;
import lombok.Data;

@Data
public class ShoppingListResource {

    private String shoppingListId;
    private String name;
    private List<CocktailResource> cocktail;

    public ShoppingListResource(String shoppingListId, String name, List<CocktailResource> cocktail) {
        this.shoppingListId = shoppingListId;
        this.name = name;
        this.cocktail = cocktail;
    }
}
