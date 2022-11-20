package com.ezgroceries.shoppinglist.jpa;

import com.ezgroceries.shoppinglist.cocktail.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.jpa.cocktail.CocktailEntity;
import com.ezgroceries.shoppinglist.jpa.cocktail.CocktailRepository;
import com.ezgroceries.shoppinglist.jpa.shoppinglist.ShoppingListEntity;
import com.ezgroceries.shoppinglist.jpa.shoppinglist.ShoppingListRepository;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CocktailServiceImpl implements CocktailService {

    private final ShoppingListRepository shoppingListRepository;
    private final CocktailRepository cocktailRepository;

    private static final Logger log = LoggerFactory.getLogger(CocktailServiceImpl.class);



    public CocktailServiceImpl(ShoppingListRepository shoppingListRepository,
            CocktailRepository cocktailRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.cocktailRepository = cocktailRepository;
    }

    @Override
    public ShoppingListResource update(String shoppingListId, String cocktailId) {
        Optional<CocktailEntity> cocktailEntity = cocktailRepository.findById(UUID.fromString(cocktailId));
        if(cocktailEntity.isPresent()) {
            log.info("Cocktail present in DB {}" , cocktailId );
            Optional<ShoppingListEntity> shoppingListEntity = shoppingListRepository.findById(UUID.fromString(shoppingListId));
            if(shoppingListEntity.isPresent()) {
                cocktailEntity.get().addShoppingLists(shoppingListEntity.get());
                return new ShoppingListResource(shoppingListEntity.get().getEntityId().toString(), shoppingListEntity.get().getName(), null); //only concern to get id in entitylocation ,so not focusing much on whole object
            }
        }
        return new ShoppingListResource(shoppingListId, null, null);
    }
}
