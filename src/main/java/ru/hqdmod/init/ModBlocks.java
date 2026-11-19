package ru.hqdmod.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.hqdmod.HQDMod;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, HQDMod.MOD_ID);

    public static final Supplier<LiquidBlock> VAPE_LIQUID_BLOCK = BLOCKS.register("vape_liquid_block",
            () -> new LiquidBlock(ModFluids.VAPE_LIQUID_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
}
