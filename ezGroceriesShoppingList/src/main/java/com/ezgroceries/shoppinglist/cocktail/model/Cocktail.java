package com.ezgroceries.shoppinglist.cocktail.model;

import java.util.List;
import lombok.Data;

@Data
public class Cocktail {

    private String id;
    private String name;
    private String glass;
    private String instructions;
    private String image;
    private List<String> ingredients;

    public Cocktail(String id, String name, String glass, String instructions, String image, List<String> ingredients) {
        this.id = id;
        this.name = name;
        this.glass = glass;
        this.instructions = instructions;
        this.image = image;
        this.ingredients = ingredients;
    }
}
