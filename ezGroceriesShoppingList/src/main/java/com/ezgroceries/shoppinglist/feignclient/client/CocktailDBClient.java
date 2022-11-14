package com.ezgroceries.shoppinglist.feignclient.client;

import com.ezgroceries.shoppinglist.feignclient.model.CocktailDBResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "cocktailDBClient", url = "https://www.thecocktaildb.com/api/json/v1/1")
public interface CocktailDBClient {

    @GetMapping(value = "search.php")  //URL:https://www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita
    CocktailDBResponse searchCocktails(@RequestParam("s") String search);

}
