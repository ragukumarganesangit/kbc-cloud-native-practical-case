package com.ezgroceries.shoppinglist.jpa.helper;

import com.ezgroceries.shoppinglist.feignclient.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.feignclient.model.CocktailDBResponse;
import com.ezgroceries.shoppinglist.feignclient.model.CocktailDBResponse.DrinkResource;
import com.ezgroceries.shoppinglist.jpa.cocktail.CocktailEntity;
import com.ezgroceries.shoppinglist.jpa.cocktail.CocktailRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CocktailDataUpdate {

    private CocktailDBClient dbClient;
    private CocktailRepository cocktailRepository;

    private static final Logger log = LoggerFactory.getLogger(CocktailDataUpdate.class);

    public CocktailDataUpdate(@Lazy CocktailDBClient dbClient, @Lazy CocktailRepository cocktailRepository) {
        this.dbClient = dbClient;
        this.cocktailRepository = cocktailRepository;
    }

    @Pointcut("execution(* com.ezgroceries.shoppinglist.cocktail.service.GroceriesService.*(..))")
    public void updateCocktailDatabase() {
    }

    @Before("updateCocktailDatabase()")
    public void mergeCocktails() {

        log.info("Aspect is called to update cocktail table");
        log.info("Number of rows before update {}", cocktailRepository.count());

        CocktailDBResponse cocktailDBResponse = dbClient.searchCocktails("");
        List<DrinkResource> drinks = cocktailDBResponse.getDrinks();
        //Get all the idDrink attributes
        List<String> ids=drinks.stream().map(CocktailDBResponse.DrinkResource::getIdDrink).collect(Collectors.toList());

        //Get all the ones we already have from our DB, use a Map for convenient lookup
        Map<String, CocktailEntity> existingEntityMap=cocktailRepository.findByDrinkIdIn(ids).stream().collect(Collectors.toMap(CocktailEntity::getDrinkId,o->o,(o,o2)->o));

        //Stream over all the drinks, map them to the existing ones, persist a new one if not existing
        Map<String, CocktailEntity> allEntityMap=drinks.stream().map(drinkResource->{
            CocktailEntity cocktailEntity=existingEntityMap.get(drinkResource.getIdDrink());
            if(cocktailEntity==null){
                CocktailEntity newCocktailEntity=new CocktailEntity();
                newCocktailEntity.setEntityId(UUID.randomUUID());
                newCocktailEntity.setDrinkId(drinkResource.getIdDrink());
                newCocktailEntity.setName(drinkResource.getStrDrink());
                newCocktailEntity.setIngredients(new HashSet<>(List.of(drinkResource.getStrIngredient1() != null ? drinkResource.getStrIngredient1():" ",
                        drinkResource.getStrIngredient2() != null ? drinkResource.getStrIngredient2():" ", drinkResource.getStrIngredient3() != null ? drinkResource.getStrIngredient3():" ")));
                newCocktailEntity.setGlass(drinkResource.getStrGlass());
                newCocktailEntity.setImage(drinkResource.getStrDrinkThumb());
                newCocktailEntity.setInstructions(drinkResource.getStrInstructions());
                cocktailEntity=cocktailRepository.save(newCocktailEntity);
            }
            return cocktailEntity;
        }).collect(Collectors.toMap(CocktailEntity::getDrinkId,o->o,(o,o2)->o));
        log.info("Number of rows after update {}", cocktailRepository.count());
    }
}
