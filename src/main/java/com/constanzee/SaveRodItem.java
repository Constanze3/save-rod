package com.constanzee;

import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.Item;

public class SaveRodItem extends Item {

    public SaveRodItem(Settings settings) {
        super(settings.food(FoodComponents.APPLE));
    }


}
