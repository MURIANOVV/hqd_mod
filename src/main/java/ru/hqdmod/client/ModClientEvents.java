package ru.hqdmod.client;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import ru.hqdmod.HQDMod;
import ru.hqdmod.init.ModFluids;

public class ModClientEvents {
    public static final ResourceLocation STILL_TEXTURE = ResourceLocation.fromNamespaceAndPath(HQDMod.MOD_ID, "block/vape_liquid_still");
    public static final ResourceLocation FLOWING_TEXTURE = ResourceLocation.fromNamespaceAndPath(HQDMod.MOD_ID, "block/vape_liquid_flow");

    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return STILL_TEXTURE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_TEXTURE;
            }

            @Override
            public int getTintColor() {
                return 0xFFFFFFFF;
            }
        }, ModFluids.VAPE_LIQUID_TYPE.get());
    }
}
