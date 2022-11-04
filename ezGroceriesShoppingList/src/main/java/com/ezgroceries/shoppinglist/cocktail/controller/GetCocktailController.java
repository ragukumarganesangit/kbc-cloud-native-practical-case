package com.ezgroceries.shoppinglist.cocktail.controller;

import com.ezgroceries.shoppinglist.cocktail.model.Cocktail;
import com.ezgroceries.shoppinglist.cocktail.model.ShoppingList;
import com.ezgroceries.shoppinglist.cocktail.service.GroceriesServiceImpl;
import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class GetCocktailController {

    private static final Logger log = LoggerFactory.getLogger(GetCocktailController.class);

    private GroceriesServiceImpl groceriesService;

    public GetCocktailController(GroceriesServiceImpl cocktailService) {
        this.groceriesService = cocktailService;
    }

    @GetMapping(value = "/cocktails")
    public List<Cocktail> getCocktails(@RequestParam(value = "search", required = false) String search) {
        log.info("Get cocktail called for name {}", search);
        return groceriesService.getCocktailByName(search);
    }

    @PostMapping(value = "/shopping-lists")
    public ResponseEntity<Void> createShoppingList(@RequestBody String shoppingListName) {
        ShoppingList shoppingList = groceriesService.createShoppingList(shoppingListName);
        return entityWithLocation(shoppingList.getShoppingListId());
    }

    @PutMapping(value = "/shopping-lists/{shoppingListId}/cocktails")
    public ResponseEntity<Void> addCocktailToShoppingList(@RequestBody String cocktailId, @PathVariable String shoppingListId) {
        ShoppingList shoppingList = groceriesService.addCocktailToShoppingList(shoppingListId, cocktailId);
        return entityWithLocation(shoppingList.getShoppingListId());
    }

    @GetMapping(value = {"/shopping-lists/", "/shopping-lists/{shoppingListId}"})
    public List<ShoppingList> getShoppingLists(@PathVariable(required = false) String shoppingListId) {
        log.info("Get shopping lists called for id {}", shoppingListId);
        return groceriesService.getShoppingListById(shoppingListId);
    }

    private ResponseEntity<Void> entityWithLocation(Object resourceId) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{resourceId}")
                .buildAndExpand(resourceId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * Maps IllegalArgumentExceptions to a 404 Not Found HTTP status code.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleNotFound(Exception ex) {
        log.error("Exception is: ", ex);
        // return empty 404
    }

}
