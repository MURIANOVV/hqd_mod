package ru.hqdmod.init;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.hqdmod.HQDMod;
import ru.hqdmod.item.HQDFlavor;
import ru.hqdmod.item.HQDItem;
import ru.hqdmod.item.VapeLiquidBottleItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HQDMod.MOD_ID);

    private static final List<DeferredItem<HQDItem>> HQD_LIST = new ArrayList<>();

    public static final DeferredItem<HQDItem> ICE_MINT_HQD = registerHQD("ice_mint_hqd", HQDFlavor.ICE_MINT);
    public static final DeferredItem<HQDItem> PINEAPPLE_HQD = registerHQD("pineapple_hqd", HQDFlavor.PINEAPPLE);
    public static final DeferredItem<HQDItem> STRAWBERRY_HQD = registerHQD("strawberry_hqd", HQDFlavor.STRAWBERRY);
    public static final DeferredItem<HQDItem> MIXED_FRUIT_HQD = registerHQD("mixed_fruit_hqd", HQDFlavor.MIXED_FRUIT);
    public static final DeferredItem<HQDItem> NUTS_TOBACCO_HQD = registerHQD("nuts_tobacco_hqd", HQDFlavor.NUTS_TOBACCO);
    public static final DeferredItem<HQDItem> ORANGE_SODA_HQD = registerHQD("orange_soda_hqd", HQDFlavor.ORANGE_SODA);
    public static final DeferredItem<HQDItem> BLUEBERRY_HQD = registerHQD("blueberry_hqd", HQDFlavor.BLUEBERRY);
    public static final DeferredItem<HQDItem> LYCHEE_ICE_HQD = registerHQD("lychee_ice_hqd", HQDFlavor.LYCHEE_ICE);
    public static final DeferredItem<HQDItem> ICE_MANGO_HQD = registerHQD("ice_mango_hqd", HQDFlavor.ICE_MANGO);
    public static final DeferredItem<HQDItem> GRAPEY_HQD = registerHQD("grapey_hqd", HQDFlavor.GRAPEY);
    public static final DeferredItem<HQDItem> LUSH_ICE_HQD = registerHQD("lush_ice_hqd", HQDFlavor.LUSH_ICE);
    public static final DeferredItem<HQDItem> TOBACCO_HQD = registerHQD("tobacco_hqd", HQDFlavor.TOBACCO);
    public static final DeferredItem<HQDItem> PEACH_ICE_HQD = registerHQD("peach_ice_hqd", HQDFlavor.PEACH_ICE);
    public static final DeferredItem<HQDItem> COLA_SODA_HQD = registerHQD("cola_soda_hqd", HQDFlavor.COLA_SODA);
    public static final DeferredItem<HQDItem> BANANA_ICE_HQD = registerHQD("banana_ice_hqd", HQDFlavor.BANANA_ICE);

    public static final DeferredItem<BucketItem> VAPE_LIQUID_BUCKET = ITEMS.register("vape_liquid_bucket",
            () -> new BucketItem(ModFluids.VAPE_LIQUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))
    );

    public static final DeferredItem<VapeLiquidBottleItem> VAPE_LIQUID_BOTTLE = ITEMS.register("vape_liquid_bottle",
            () -> new VapeLiquidBottleItem(new Item.Properties())
    );

    private static DeferredItem<HQDItem> registerHQD(String name, HQDFlavor flavor) {
        DeferredItem<HQDItem> item = ITEMS.register(name, () -> new HQDItem(flavor, new Item.Properties()));
        HQD_LIST.add(item);
        return item;
    }

    public static List<DeferredItem<HQDItem>> getAllHqds() {
        return Collections.unmodifiableList(HQD_LIST);
    }
}
