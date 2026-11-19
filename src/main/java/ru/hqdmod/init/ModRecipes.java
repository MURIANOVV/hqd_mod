package ru.hqdmod.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.hqdmod.HQDMod;
import ru.hqdmod.recipe.HQDRechargeRecipe;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, HQDMod.MOD_ID);

    public static final Supplier<RecipeSerializer<?>> RECHARGE_SERIALIZER = RECIPE_SERIALIZERS.register(
            "recharge",
            () -> new SimpleCraftingRecipeSerializer<>(HQDRechargeRecipe::new)
    );
}
