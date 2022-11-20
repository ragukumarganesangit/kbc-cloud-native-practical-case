package com.ezgroceries.shoppinglist.jpa.cocktail;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CocktailRepository extends CrudRepository<CocktailEntity, UUID> {
    public List<CocktailEntity> findByDrinkIdIn(List<String> drinkId);
}
