package com.ezgroceries.shoppinglist.cocktail.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ezgroceries.shoppinglist.cocktail.CocktailTestConfiguration;
import com.ezgroceries.shoppinglist.cocktail.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.feignclient.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.feignclient.model.CocktailDBResponse;
import com.ezgroceries.shoppinglist.jpa.CocktailService;
import com.ezgroceries.shoppinglist.jpa.ShoppingListService;
import com.ezgroceries.shoppinglist.jpa.shoppinglist.ShoppingListRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = CocktailController.class)
@ContextConfiguration(classes = {CocktailController.class, CocktailTestConfiguration.class, ShoppingListRepository.class})
class CocktailEntityControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CocktailDBClient cocktailDBClient;

    @Autowired
    private CocktailDBResponse cocktailDBResponse;

    @Autowired
    private CocktailDBResponse cocktailDBResponseAllData;

    @MockBean
    private ShoppingListService shoppingListService;

    @MockBean
    private CocktailService cocktailService;

    @Test
    public void testGetAllCocktailController() throws Exception {
        when(cocktailDBClient.searchCocktails("")).thenReturn(cocktailDBResponseAllData);
        mvc.perform(get("/cocktails"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].name", equalTo("Margerita")))
                .andExpect(jsonPath("$.[1].name", equalTo("Blue Margerita")))
                .andExpect(jsonPath("$.size()", equalTo(2)));
    }

    @Test
    public void testGetCocktailByNameController() throws Exception {

        when(cocktailDBClient.searchCocktails(anyString())).thenReturn(cocktailDBResponse);

        mvc.perform(get("/cocktails?search=Margerita"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].name", equalTo("Margerita")))
                .andExpect(jsonPath("$.size()", equalTo(1)));
    }

    @Test
    public void testGetCocktailControllerNull() throws Exception {
        when(cocktailDBClient.searchCocktails(anyString())).thenReturn(cocktailDBResponse);
        mvc.perform(get("/cocktails?search=test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", equalTo(0)));
    }

    @Test
    public void testCreateShoppingListController() throws Exception {
        when(shoppingListService.create(anyString())).thenReturn(new ShoppingListResource("90689338-499a-4c49-af90-f1e73068ad4f", "Stephanie's birthday", null));
        mvc.perform(post("/shopping-lists").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content("Stephanie's birthday"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/shopping-lists/90689338-499a-4c49-af90-f1e73068ad4f"));
    }

    @Test
    public void testAddCocktailToShoppingListController() throws Exception {
        when(cocktailService.update(anyString(), anyString())).thenReturn(new ShoppingListResource("90689338-499a-4c49-af90-f1e73068ad4f", "Stephanie's birthday", null));
        mvc.perform(put("/shopping-lists/90689338-499a-4c49-af90-f1e73068ad4f/cocktails").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content("{\"cocktailId\": \"23b3d85a-3928-41c0-a533-6538a71e17c4\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/shopping-lists/90689338-499a-4c49-af90-f1e73068ad4f/cocktails/90689338-499a-4c49-af90-f1e73068ad4f"));
    }

    @Test
    public void testGetShoppingListsController() throws Exception {
        when(shoppingListService.getShoppingLists()).thenReturn(
                List.of(new ShoppingListResource("90689338-499a-4c49-af90-f1e73068ad4f", "Stephanie's birthday", null),
                        new ShoppingListResource("23b3d85a-3928-41c0-a533-6538a71e17c4", "Ragu's birthday", null),
                        new ShoppingListResource("d615ec78-fe93-467b-8d26-5d26d8eab073", "Ramya's birthday", null)));
        mvc.perform(get("/shopping-lists/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].shoppingListId", equalTo("90689338-499a-4c49-af90-f1e73068ad4f")))
                .andExpect(jsonPath("$.size()", equalTo(3)));
    }
}