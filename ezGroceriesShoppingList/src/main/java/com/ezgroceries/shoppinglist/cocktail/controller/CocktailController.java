package com.ezgroceries.shoppinglist.cocktail.controller;

import com.ezgroceries.shoppinglist.cocktail.model.CocktailResource;
import com.ezgroceries.shoppinglist.cocktail.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.cocktail.service.GroceriesService;
import com.ezgroceries.shoppinglist.cocktail.service.GroceriesServiceImpl;
import com.ezgroceries.shoppinglist.jpa.CocktailService;
import com.ezgroceries.shoppinglist.jpa.ShoppingListService;
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
public class CocktailController {

    private static final Logger log = LoggerFactory.getLogger(CocktailController.class);

    private GroceriesService groceriesService;

    private ShoppingListService shoppingListService;

    private CocktailService cocktailService;

    public CocktailController(GroceriesServiceImpl groceriesService, ShoppingListService shoppingListService,
            CocktailService cocktailService) {
        this.groceriesService = groceriesService;
        this.shoppingListService = shoppingListService;
        this.cocktailService = cocktailService;
    }

    @GetMapping(value = "/cocktails")
    public List<CocktailResource> getCocktails(@RequestParam(value = "search", required = false) String search) {
        log.info("Get cocktail called for name {}", search);
        return groceriesService.getCocktailByName(search == null ? "" : search);
    }

    @PostMapping(value = "/shopping-lists")
    public ResponseEntity<Void> createShoppingList(@RequestBody String shoppingListName) {
        ShoppingListResource shoppingList = shoppingListService.create(shoppingListName);
        return entityWithLocation(shoppingList.getShoppingListId());
    }

    @PutMapping(value = "/shopping-lists/{shoppingListId}/cocktails")
    public ResponseEntity<Void> addCocktailToShoppingList(@RequestBody String cocktailId, @PathVariable String shoppingListId) {
        ShoppingListResource shoppingList = cocktailService.update(shoppingListId, cocktailId);
        return entityWithLocation(shoppingList.getShoppingListId());
    }

    @GetMapping(value = {"/shopping-lists/{shoppingListId}"})
    public ShoppingListResource getShoppingList(@PathVariable(required = false) String shoppingListId) {
        log.info("Get shopping lists called for id {}", shoppingListId);
        return shoppingListService.getShoppingListById(shoppingListId);
    }

    @GetMapping(value = {"/shopping-lists/"})
    public List<ShoppingListResource> getShoppingLists() {
        return shoppingListService.getShoppingLists();
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
