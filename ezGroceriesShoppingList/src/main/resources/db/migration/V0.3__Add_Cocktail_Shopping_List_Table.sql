create table COCKTAIL_SHOPPING_LIST
(
    COCKTAIL_ID   UUID REFERENCES COCKTAIL (ID) ON UPDATE CASCADE ON DELETE CASCADE,
    SHOPPING_LIST_ID_DRINK UUID REFERENCES SHOPPING_LIST (ID) ON UPDATE CASCADE ON DELETE CASCADE
);