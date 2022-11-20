package com.ezgroceries.shoppinglist.jpa.shoppinglist;

import com.ezgroceries.shoppinglist.jpa.cocktail.CocktailEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * List contains the shopping details
 */
@Entity
@Table(name = "SHOPPING_LIST")
public class ShoppingListEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private UUID entityId;

    // No need for @Column, mapped automatically to NAME
	private String name;

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			},
			mappedBy = "shoppingLists")
	@JsonIgnore
	private Set<CocktailEntity> cocktailEntities = new HashSet<>();

	public ShoppingListEntity(String name) {
		this.name = name;
	}

	public ShoppingListEntity() {
	}

	public UUID getEntityId() {
		return entityId;
	}

	public void setEntityId(UUID entityId) {
		this.entityId = entityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ShoppingListEntity)) {
			return false;
		}
		ShoppingListEntity that = (ShoppingListEntity) o;
		return Objects.equals(getEntityId(), that.getEntityId()) && Objects.equals(getName(), that.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getEntityId(), getName());
	}

	@Override
	public String toString() {
		return "ShoppingList{" +
				"entityId=" + entityId +
				", name='" + name + '\'' +
				'}';
	}

	public Set<CocktailEntity> getCocktailEntities() {
		return cocktailEntities;
	}
}