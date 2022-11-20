package com.ezgroceries.shoppinglist.jpa.cocktail;

import com.ezgroceries.shoppinglist.jpa.shoppinglist.ShoppingListEntity;
import com.ezgroceries.shoppinglist.jpa.helper.StringSetConverter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * A cocktail contains list of ingredients and its associated name.
 */
@Entity
@Table(name = "COCKTAIL")
public class CocktailEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private UUID entityId;

	@Column(name = "ID_DRINK")
	private String drinkId;

    // No need for @Column, mapped automatically to NAME
	private String name;

	// No need for @Column, mapped automatically to ingredients
	@Convert(converter = StringSetConverter.class)
	private Set<String> ingredients;

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	@JoinTable(name = "cocktail_shopping_list",
			joinColumns = { @JoinColumn(name = "COCKTAIL_ID") },
			inverseJoinColumns = { @JoinColumn(name = "SHOPPING_LIST_ID_DRINK") })
	private Set<ShoppingListEntity> shoppingLists = new HashSet<>();

	//calling this method in addcoktailtoshoppinglist or addshoppinglisttococktail will create link
	public void addShoppingLists(ShoppingListEntity shoppingListEntity) {
		this.shoppingLists.add(shoppingListEntity);
		shoppingListEntity.getCocktailEntities().add(this);
	}

	public CocktailEntity() {
		//Needed by the JPA spec
	}

	public CocktailEntity(String drinkId, String name, Set<String> ingredients) {
		this.drinkId = drinkId;
		this.name = name;
		this.ingredients = ingredients;
	}

	public UUID getEntityId() {
		return entityId;
	}

	public void setEntityId(UUID entityId) {
		this.entityId = entityId;
	}

	public String getDrinkId() {
		return drinkId;
	}

	public void setDrinkId(String drinkId) {
		this.drinkId = drinkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<String> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CocktailEntity)) {
			return false;
		}
		CocktailEntity cocktailEntity = (CocktailEntity) o;
		return Objects.equals(getEntityId(), cocktailEntity.getEntityId()) && Objects.equals(getDrinkId(), cocktailEntity.getDrinkId())
				&& Objects.equals(getName(), cocktailEntity.getName()) && Objects.equals(getIngredients(), cocktailEntity.getIngredients());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getEntityId(), getDrinkId(), getName(), getIngredients());
	}

	@Override
	public String toString() {
		return "Cocktail{" +
				"entityId=" + entityId +
				", drinkId='" + drinkId + '\'' +
				", name='" + name + '\'' +
				", ingredients='" + ingredients + '\'' +
				'}';
	}
}