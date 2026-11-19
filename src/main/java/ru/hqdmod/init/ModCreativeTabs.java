package ru.hqdmod.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.hqdmod.HQDMod;

import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HQDMod.MOD_ID);

    public static final Supplier<CreativeModeTab> HQD_TAB = CREATIVE_MODE_TABS.register("hqd_tab", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.hqdmod"))
                    .icon(() -> new ItemStack(ModItems.ICE_MINT_HQD.get()))
                    .displayItems((parameters, output) -> {
                        for (var hqd : ModItems.getAllHqds()) {
                            output.accept(hqd.get());
                        }
                        output.accept(ModItems.VAPE_LIQUID_BOTTLE.get());
                        output.accept(ModItems.VAPE_LIQUID_BUCKET.get());
                    })
                    .build()
    );
}
