package ru.hqdmod.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ModConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfig CONFIG;

    public final ModConfigSpec.IntValue maxCharge;
    public final ModConfigSpec.IntValue rechargeAmount;
    public final ModConfigSpec.IntValue effectDurationTicks;
    public final ModConfigSpec.IntValue cooldownTicks;
    public final ModConfigSpec.BooleanValue enableParticles;
    public final ModConfigSpec.BooleanValue enableSounds;

    static {
        Pair<ModConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(ModConfig::new);
        CONFIG = pair.getLeft();
        SPEC = pair.getRight();
    }

    public ModConfig(ModConfigSpec.Builder builder) {
        builder.push("general");

        maxCharge = builder
                .comment("Maximum charge for HQD devices (default: 100 uses)")
                .defineInRange("maxCharge", 100, 1, 1000);

        rechargeAmount = builder
                .comment("Number of charges restored by one Vape Liquid bottle (default: 50)")
                .defineInRange("rechargeAmount", 50, 1, 1000);

        effectDurationTicks = builder
                .comment("Duration of HQD effects in ticks (600 ticks = 30 seconds)")
                .defineInRange("effectDurationTicks", 600, 20, 72000);

        cooldownTicks = builder
                .comment("Cooldown between uses in ticks (25 ticks = 1.25 seconds)")
                .defineInRange("cooldownTicks", 25, 0, 200);

        enableParticles = builder
                .comment("Enable smoke particles when using HQD")
                .define("enableParticles", true);

        enableSounds = builder
                .comment("Enable sound effects when using HQD")
                .define("enableSounds", true);

        builder.pop();
    }

    public int getMaxCharge() {
        try {
            int val = maxCharge.get();
            return Math.max(100, val);
        } catch (Exception e) {
            return 100;
        }
    }

    public int getRechargeAmount() {
        try {
            int val = rechargeAmount.get();
            return Math.max(50, val);
        } catch (Exception e) {
            return 50;
        }
    }
}
