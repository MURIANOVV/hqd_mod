package ru.hqdmod.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import ru.hqdmod.config.ModConfig;
import ru.hqdmod.init.ModDataComponents;
import ru.hqdmod.init.ModItems;
import ru.hqdmod.init.ModRecipes;
import ru.hqdmod.item.HQDItem;

public class HQDRechargeRecipe extends CustomRecipe {
    public HQDRechargeRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        ItemStack hqdStack = ItemStack.EMPTY;
        ItemStack bottleStack = ItemStack.EMPTY;
        int occupied = 0;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;
            occupied++;

            if (stack.getItem() instanceof HQDItem && hqdStack.isEmpty()) {
                hqdStack = stack;
            } else if (stack.is(ModItems.VAPE_LIQUID_BOTTLE.get()) && bottleStack.isEmpty()) {
                bottleStack = stack;
            } else {
                return false;
            }
        }

        if (occupied != 2 || hqdStack.isEmpty() || bottleStack.isEmpty()) {
            return false;
        }

        HQDItem hqdItem = (HQDItem) hqdStack.getItem();
        int maxCharge = hqdItem.getMaxCharge();
        return hqdItem.getCharge(hqdStack) < maxCharge;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.getItem() instanceof HQDItem hqdItem) {
                int currentCharge = hqdItem.getCharge(stack);
                int maxCharge = hqdItem.getMaxCharge();
                if (currentCharge < maxCharge) {
                    ItemStack result = stack.copyWithCount(1);
                    int restored = Math.min(maxCharge, currentCharge + ModConfig.CONFIG.getRechargeAmount());
                    result.set(ModDataComponents.CHARGE.get(), restored);
                    return result;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.is(ModItems.VAPE_LIQUID_BOTTLE.get())) {
                remaining.set(i, new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return remaining;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.RECHARGE_SERIALIZER.get();
    }
}
