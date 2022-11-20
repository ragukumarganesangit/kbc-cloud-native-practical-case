package com.ezgroceries.shoppinglist.jpa;

import com.ezgroceries.shoppinglist.cocktail.model.CocktailResource;
import com.ezgroceries.shoppinglist.cocktail.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.jpa.cocktail.CocktailEntity;
import com.ezgroceries.shoppinglist.jpa.shoppinglist.ShoppingListEntity;
import com.ezgroceries.shoppinglist.jpa.shoppinglist.ShoppingListRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ShoppingListServiceImpl implements ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;

    public ShoppingListServiceImpl(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    @Override
    public ShoppingListResource create(String shoppingListName) {
        ShoppingListEntity save = shoppingListRepository.save(new ShoppingListEntity(shoppingListName));
        return new ShoppingListResource(save.getEntityId().toString(), save.getName(), null);
    }

    @Override
    public ShoppingListResource getShoppingListById(String shoppingListId) {
        Optional<ShoppingListEntity> byId = shoppingListRepository.findById(UUID.fromString(shoppingListId));
        if(byId.isPresent()) {
            ShoppingListEntity shoppingListEntity = byId.get();
            List<CocktailResource> cocktailResourceList = new ArrayList<>();
            for(CocktailEntity cocktailEntity: shoppingListEntity.getCocktailEntities()) {
                CocktailResource cocktailResource = new CocktailResource();
                cocktailResource.setId(cocktailEntity.getEntityId().toString());
                cocktailResource.setName(cocktailEntity.getName());
                cocktailResource.setIngredients(new ArrayList<>(cocktailEntity.getIngredients()));
                cocktailResourceList.add(cocktailResource);
            }
            return new ShoppingListResource(shoppingListEntity.getEntityId().toString(), shoppingListEntity.getName(), cocktailResourceList);
        }
        return null;
    }

    public List<ShoppingListResource> getShoppingLists() {
        List<ShoppingListResource> shoppingListResourceList = new ArrayList<>();
        Iterable<ShoppingListEntity> shoppingListEntities = shoppingListRepository.findAll();
        for(ShoppingListEntity shoppingListEntity : shoppingListEntities) {
            List<CocktailResource> cocktailResourceList = new ArrayList<>();
            for(CocktailEntity cocktailEntity: shoppingListEntity.getCocktailEntities()) {
                CocktailResource cocktailResource = new CocktailResource();
                cocktailResource.setId(cocktailEntity.getEntityId().toString());
                cocktailResource.setName(cocktailEntity.getName());
                cocktailResource.setIngredients(new ArrayList<>(cocktailEntity.getIngredients()));
                cocktailResourceList.add(cocktailResource);
            }
            ShoppingListResource shoppingListResource = new ShoppingListResource(shoppingListEntity.getEntityId().toString(), shoppingListEntity.getName(), cocktailResourceList);
            shoppingListResourceList.add(shoppingListResource);
        }
        return shoppingListResourceList;
    }

}
