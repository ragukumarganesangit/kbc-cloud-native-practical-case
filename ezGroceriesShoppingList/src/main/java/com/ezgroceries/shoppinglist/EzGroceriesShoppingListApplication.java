package com.ezgroceries.shoppinglist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.ezgroceries.shoppinglist.cocktail"})
public class EzGroceriesShoppingListApplication {

	public static void main(String[] args) {
		SpringApplication.run(EzGroceriesShoppingListApplication.class, args);
	}

}
