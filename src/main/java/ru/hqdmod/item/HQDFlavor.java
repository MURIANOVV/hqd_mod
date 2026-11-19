package ru.hqdmod.item;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

import java.util.function.Supplier;

public enum HQDFlavor {
    ICE_MINT(
            "ice_mint",
            () -> MobEffects.MOVEMENT_SPEED, 0,
            () -> MobEffects.JUMP, 0,
            0x2ECC71
    ),
    PINEAPPLE(
            "pineapple",
            () -> MobEffects.DIG_SPEED, 0,
            () -> MobEffects.SATURATION, 0,
            0xF39C12
    ),
    STRAWBERRY(
            "strawberry",
            () -> MobEffects.REGENERATION, 0,
            () -> MobEffects.ABSORPTION, 0,
            0xE84393
    ),
    MIXED_FRUIT(
            "mixed_fruit",
            () -> MobEffects.MOVEMENT_SPEED, 0,
            () -> MobEffects.LUCK, 0,
            0xFF4757
    ),
    NUTS_TOBACCO(
            "nuts_tobacco",
            () -> MobEffects.DAMAGE_RESISTANCE, 0,
            () -> MobEffects.DAMAGE_BOOST, 0,
            0x795548
    ),
    ORANGE_SODA(
            "orange_soda",
            () -> MobEffects.MOVEMENT_SPEED, 1, // Speed II
            () -> MobEffects.DIG_SPEED, 0,      // Haste I
            0xFF793F
    ),
    BLUEBERRY(
            "blueberry",
            () -> MobEffects.NIGHT_VISION, 0,
            () -> MobEffects.LUCK, 0,
            0x2E86DE
    ),
    LYCHEE_ICE(
            "lychee_ice",
            () -> MobEffects.JUMP, 1,           // Jump Boost II
            () -> MobEffects.REGENERATION, 0,   // Regeneration I
            0xF8A5C2
    ),
    ICE_MANGO(
            "ice_mango",
            () -> MobEffects.FIRE_RESISTANCE, 0,
            () -> MobEffects.MOVEMENT_SPEED, 0,
            0xE67E22
    ),
    GRAPEY(
            "grapey",
            () -> MobEffects.DAMAGE_BOOST, 0,
            () -> MobEffects.DIG_SPEED, 0,
            0x8E44AD
    ),
    LUSH_ICE(
            "lush_ice",
            () -> MobEffects.MOVEMENT_SPEED, 0,
            () -> MobEffects.WATER_BREATHING, 0,
            0x27AE60
    ),
    TOBACCO(
            "tobacco",
            () -> MobEffects.DAMAGE_RESISTANCE, 0,
            () -> MobEffects.ABSORPTION, 0,
            0x2F3542
    ),
    PEACH_ICE(
            "peach_ice",
            () -> MobEffects.REGENERATION, 0,
            () -> MobEffects.JUMP, 0,
            0xFFB8B8
    ),
    COLA_SODA(
            "cola_soda",
            () -> MobEffects.DIG_SPEED, 1,      // Haste II
            () -> MobEffects.DAMAGE_BOOST, 0,   // Strength I
            0x881122
    ),
    BANANA_ICE(
            "banana_ice",
            () -> MobEffects.SATURATION, 0,
            () -> MobEffects.MOVEMENT_SPEED, 0,
            0xF1C40F
    );

    private final String flavorId;
    private final Supplier<Holder<MobEffect>> effect1;
    private final int amplifier1;
    private final Supplier<Holder<MobEffect>> effect2;
    private final int amplifier2;
    private final int barColor;

    HQDFlavor(String flavorId,
              Supplier<Holder<MobEffect>> effect1, int amplifier1,
              Supplier<Holder<MobEffect>> effect2, int amplifier2,
              int barColor) {
        this.flavorId = flavorId;
        this.effect1 = effect1;
        this.amplifier1 = amplifier1;
        this.effect2 = effect2;
        this.amplifier2 = amplifier2;
        this.barColor = barColor;
    }

    public String getFlavorId() {
        return flavorId;
    }

    public Supplier<Holder<MobEffect>> getEffect1() {
        return effect1;
    }

    public int getAmplifier1() {
        return amplifier1;
    }

    public Supplier<Holder<MobEffect>> getEffect2() {
        return effect2;
    }

    public int getAmplifier2() {
        return amplifier2;
    }

    public int getBarColor() {
        return barColor;
    }
}
