package com.ezgroceries.shoppinglist.feignclient.client;

import com.ezgroceries.shoppinglist.feignclient.model.CocktailDBResponse;
import com.ezgroceries.shoppinglist.jpa.cocktail.CocktailEntity;
import com.ezgroceries.shoppinglist.jpa.cocktail.CocktailRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "cocktailDBClient", url = "https://www.thecocktaildb.com/api/json/v1/1", fallback = CocktailDBClient.CocktailDBClientFallback.class)
public interface CocktailDBClient {

    @GetMapping(value = "search.php")  //URL:https://www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita
    CocktailDBResponse searchCocktails(@RequestParam(value = "s", required = false) String search);


    @Component
    class CocktailDBClientFallback implements CocktailDBClient {

        private final CocktailRepository cocktailRepository;

        public CocktailDBClientFallback(CocktailRepository cocktailRepository) {
            this.cocktailRepository = cocktailRepository;
        }

        @Override
        public CocktailDBResponse searchCocktails(String search) {
            List<CocktailEntity> cocktailEntities = cocktailRepository.findByName(search);

            CocktailDBResponse cocktailDBResponse = new CocktailDBResponse();
            cocktailDBResponse.setDrinks(cocktailEntities.stream().map(cocktailEntity -> {
                CocktailDBResponse.DrinkResource drinkResource = new CocktailDBResponse.DrinkResource();
                drinkResource.setIdDrink(cocktailEntity.getDrinkId());
                drinkResource.setStrDrink(cocktailEntity.getName());
                drinkResource.setStrGlass(cocktailEntity.getGlass());
                drinkResource.setStrInstructions(cocktailEntity.getInstructions());
                return drinkResource;
            }).collect(Collectors.toList()));

            return cocktailDBResponse;
        }
    }

}
