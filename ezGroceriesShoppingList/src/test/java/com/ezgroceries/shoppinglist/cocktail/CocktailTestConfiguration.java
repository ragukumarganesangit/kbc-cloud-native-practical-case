package com.ezgroceries.shoppinglist.cocktail;

import com.ezgroceries.shoppinglist.feignclient.model.CocktailDBResponse;
import com.ezgroceries.shoppinglist.feignclient.model.CocktailDBResponse.DrinkResource;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = "com.ezgroceries.shoppinglist")
public class CocktailTestConfiguration {

    @Bean
    CocktailDBResponse cocktailDBResponse() {
        CocktailDBResponse cocktailDBResponse = new CocktailDBResponse();
        DrinkResource drinkResource = new DrinkResource();
        drinkResource.setIdDrink("testid");
        drinkResource.setStrDrink("Margerita");
        List<DrinkResource> drinks = new ArrayList<>();
        drinks.add(drinkResource);
        cocktailDBResponse.setDrinks(drinks);
        return  cocktailDBResponse;
    }

    @Bean
    CocktailDBResponse cocktailDBResponseAllData() {
        CocktailDBResponse cocktailDBResponse = new CocktailDBResponse();
        DrinkResource drinkResource1 = new DrinkResource();
        drinkResource1.setIdDrink("testid1");
        drinkResource1.setStrDrink("Margerita");
        List<DrinkResource> drinks = new ArrayList<>();
        drinks.add(drinkResource1);
        DrinkResource drinkResource2 = new DrinkResource();
        drinkResource2.setIdDrink("testid2");
        drinkResource2.setStrDrink("Blue Margerita");
        drinks.add(drinkResource2);
        cocktailDBResponse.setDrinks(drinks);
        return  cocktailDBResponse;
    }
}