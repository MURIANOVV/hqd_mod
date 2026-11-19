package ru.hqdmod.brewing;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.brewing.IBrewingRecipe;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import ru.hqdmod.init.ModItems;

public class ModBrewing implements IBrewingRecipe {

    @SubscribeEvent
    public static void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event) {
        event.getBuilder().addRecipe(new ModBrewing());
    }

    @Override
    public boolean isInput(ItemStack input) {
        if (!input.is(Items.POTION)) {
            return false;
        }
        PotionContents contents = input.get(DataComponents.POTION_CONTENTS);
        return contents != null && contents.is(Potions.WATER);
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.is(Items.SUGAR);
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if (isInput(input) && isIngredient(ingredient)) {
            return new ItemStack(ModItems.VAPE_LIQUID_BOTTLE.get());
        }
        return ItemStack.EMPTY;
    }
}
