package ru.hqdmod.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import ru.hqdmod.HQDMod;
import ru.hqdmod.fluid.VapeLiquidType;

import java.util.function.Supplier;

public class ModFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, HQDMod.MOD_ID);

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, HQDMod.MOD_ID);

    public static final Supplier<FluidType> VAPE_LIQUID_TYPE = FLUID_TYPES.register("vape_liquid",
            VapeLiquidType::new);

    public static final Supplier<FlowingFluid> VAPE_LIQUID_SOURCE = FLUIDS.register("vape_liquid",
            () -> new BaseFlowingFluid.Source(ModFluids.PROPERTIES));

    public static final Supplier<FlowingFluid> VAPE_LIQUID_FLOWING = FLUIDS.register("flowing_vape_liquid",
            () -> new BaseFlowingFluid.Flowing(ModFluids.PROPERTIES));

    public static final BaseFlowingFluid.Properties PROPERTIES = new BaseFlowingFluid.Properties(
            VAPE_LIQUID_TYPE,
            VAPE_LIQUID_SOURCE,
            VAPE_LIQUID_FLOWING
    ).slopeFindDistance(2).levelDecreasePerBlock(2).block(ModBlocks.VAPE_LIQUID_BLOCK).bucket(ModItems.VAPE_LIQUID_BUCKET);
}
