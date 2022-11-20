package com.ezgroceries.shoppinglist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.ezgroceries.shoppinglist.cocktail",
        "com.ezgroceries.shoppinglist.feignclient",
        "com.ezgroceries.shoppinglist.jpa"})
@EnableFeignClients(basePackages = "com.ezgroceries.shoppinglist")
public class EzGroceriesShoppingListApplication {

    public static void main(String[] args) {

        SpringApplication.run(EzGroceriesShoppingListApplication.class, args);
    }

}
