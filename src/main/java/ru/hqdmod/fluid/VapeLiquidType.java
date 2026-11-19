package ru.hqdmod.fluid;

import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;

public class VapeLiquidType extends FluidType {
    public VapeLiquidType() {
        super(FluidType.Properties.create()
                .descriptionId("fluid.hqdmod.vape_liquid")
                .fallDistanceModifier(0F)
                .canExtinguish(true)
                .canDrown(true)
                .supportsBoating(true)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .density(1050)
                .viscosity(1200)
        );
    }
}
