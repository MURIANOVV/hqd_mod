package ru.hqdmod;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import ru.hqdmod.brewing.ModBrewing;
import ru.hqdmod.client.ModClientEvents;
import ru.hqdmod.config.ModConfig;
import ru.hqdmod.init.*;

@Mod(HQDMod.MOD_ID)
public class HQDMod {
    public static final String MOD_ID = "hqdmod";

    public HQDMod(IEventBus modEventBus, ModContainer modContainer) {
        ModDataComponents.DATA_COMPONENT_TYPES.register(modEventBus);
        ModSounds.SOUND_EVENTS.register(modEventBus);
        ModFluids.FLUID_TYPES.register(modEventBus);
        ModFluids.FLUIDS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModRecipes.RECIPE_SERIALIZERS.register(modEventBus);

        modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, ModConfig.SPEC);

        NeoForge.EVENT_BUS.addListener(ModBrewing::onRegisterBrewingRecipes);

        modEventBus.addListener(this::buildCreativeTabContents);

        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(ModClientEvents::onRegisterClientExtensions);
        }
    }

    private void buildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            for (var item : ModItems.getAllHqds()) {
                event.accept(item.get());
            }
            event.accept(ModItems.VAPE_LIQUID_BOTTLE.get());
            event.accept(ModItems.VAPE_LIQUID_BUCKET.get());
        }
    }
}
