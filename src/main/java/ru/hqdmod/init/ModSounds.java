package ru.hqdmod.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.hqdmod.HQDMod;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, HQDMod.MOD_ID);

    public static final Supplier<SoundEvent> VAPE_PUFF = SOUND_EVENTS.register("vape_puff", () ->
            SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(HQDMod.MOD_ID, "vape_puff"))
    );

    public static final Supplier<SoundEvent> VAPE_EMPTY = SOUND_EVENTS.register("vape_empty", () ->
            SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(HQDMod.MOD_ID, "vape_empty"))
    );
}
