package org.example.inventory_write_service.entity.inventory.subcategory;

import org.example.inventory_write_service.entity.inventory.category.CategoryEnum;

public enum SubCategoryEnum {

    //Groceries

    VEGETABLE_FRUITS(CategoryEnum.GROCERY_KITCHEN),
    ATTA_RICE_DAL(CategoryEnum.GROCERY_KITCHEN),
    OIL_GHEE_MASALA(CategoryEnum.GROCERY_KITCHEN),
    DAIRY_BREAD_EGGS(CategoryEnum.GROCERY_KITCHEN),
    BAKERY(CategoryEnum.GROCERY_KITCHEN),
    DRY_FRUITS_CEREALS(CategoryEnum.GROCERY_KITCHEN),
    CHICKEN_MEAT_FISH(CategoryEnum.GROCERY_KITCHEN),
    KITCHEN_APPLIANCES(CategoryEnum.GROCERY_KITCHEN),


    //Snacks

    CHIPS(CategoryEnum.SNACKS_DRINKS),

    SWEETS_CHOCOLATES(CategoryEnum.SNACKS_DRINKS),

    DRINKS_JUICES(CategoryEnum.SNACKS_DRINKS),

    TEA_COFFEE_MILK_DRINKS(CategoryEnum.SNACKS_DRINKS),

    NON_ALCOHOL(CategoryEnum.SNACKS_DRINKS),

    ALCOHOL(CategoryEnum.SNACKS_DRINKS),

    INSTANT_FOOD(CategoryEnum.SNACKS_DRINKS),

    SAUCES_SPREADS(CategoryEnum.SNACKS_DRINKS),

    ICECREAMS_MORE(CategoryEnum.SNACKS_DRINKS);

    //Beauty Personal Care







    private final CategoryEnum category;


    SubCategoryEnum(CategoryEnum category) {
        this.category = category;
    }

    public CategoryEnum getCategory() {
        return category;
    }
}
