package ru.hqdmod.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import ru.hqdmod.config.ModConfig;
import ru.hqdmod.init.ModDataComponents;
import ru.hqdmod.init.ModItems;
import ru.hqdmod.init.ModSounds;

import java.util.List;

public class HQDItem extends Item {
    private final HQDFlavor flavor;

    public HQDItem(HQDFlavor flavor, Properties properties) {
        super(properties.stacksTo(1).component(ModDataComponents.CHARGE.get(), 100));
        this.flavor = flavor;
    }

    public HQDFlavor getFlavor() {
        return flavor;
    }

    public int getMaxCharge() {
        return ModConfig.CONFIG.getMaxCharge();
    }

    public int getCharge(ItemStack stack) {
        int max = getMaxCharge();
        return stack.getOrDefault(ModDataComponents.CHARGE.get(), max);
    }

    public void setCharge(ItemStack stack, int charge) {
        int max = getMaxCharge();
        stack.set(ModDataComponents.CHARGE.get(), Math.max(0, Math.min(max, charge)));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int charge = getCharge(stack);
        int max = getMaxCharge();
        if (max <= 0) max = 100;
        float ratio = (float) charge / (float) max;
        return Math.min(13, Math.max(0, Math.round(13.0F * ratio)));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return flavor.getBarColor();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        int charge = getCharge(stack);
        if (charge <= 0) {
            if (!level.isClientSide()) {
                player.displayClientMessage(
                        Component.translatable("message.hqdmod.empty_charge").withStyle(ChatFormatting.RED),
                        true
                );
            }
            if (ModConfig.CONFIG.enableSounds.get()) {
                level.playSound(player, player.getX(), player.getY(), player.getZ(),
                        ModSounds.VAPE_EMPTY.get(), SoundSource.PLAYERS, 0.6F, 1.0F);
            }
            return InteractionResultHolder.fail(stack);
        }

        if (!level.isClientSide()) {
            setCharge(stack, charge - 1);

            int duration = ModConfig.CONFIG.effectDurationTicks.get();
            player.addEffect(new MobEffectInstance(flavor.getEffect1().get(), duration, flavor.getAmplifier1(), false, true, true));
            player.addEffect(new MobEffectInstance(flavor.getEffect2().get(), duration, flavor.getAmplifier2(), false, true, true));

            if (ModConfig.CONFIG.enableParticles.get() && level instanceof ServerLevel serverLevel) {
                Vec3 look = player.getLookAngle();
                Vec3 eye = player.getEyePosition();
                Vec3 spawn = eye.add(look.scale(0.35));
                serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, spawn.x, spawn.y - 0.1, spawn.z, 6, 0.08, 0.08, 0.08, 0.015);
                serverLevel.sendParticles(ParticleTypes.CLOUD, spawn.x, spawn.y - 0.1, spawn.z, 3, 0.05, 0.05, 0.05, 0.01);
            }

            if (ModConfig.CONFIG.enableSounds.get()) {
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        ModSounds.VAPE_PUFF.get(), SoundSource.PLAYERS, 0.8F, 1.2F);
            }
        }

        int cooldown = ModConfig.CONFIG.cooldownTicks.get();
        if (cooldown > 0) {
            player.getCooldowns().addCooldown(this, cooldown);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot,
                                            ClickAction action, Player player, SlotAccess access) {
        if (action == ClickAction.SECONDARY && other.is(ModItems.VAPE_LIQUID_BOTTLE.get())) {
            int currentCharge = getCharge(stack);
            int maxCharge = getMaxCharge();

            if (currentCharge < maxCharge) {
                int restored = Math.min(maxCharge, currentCharge + ModConfig.CONFIG.getRechargeAmount());
                setCharge(stack, restored);

                other.shrink(1);
                ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
                if (!player.getInventory().add(bottle)) {
                    player.drop(bottle, false);
                }

                player.playSound(SoundEvents.BOTTLE_EMPTY, 0.8F, 1.2F);
                return true;
            }
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        int charge = getCharge(stack);
        int max = getMaxCharge();
        int durationSec = ModConfig.CONFIG.effectDurationTicks.get() / 20;

        tooltip.add(Component.translatable("tooltip.hqdmod.flavor",
                Component.translatable("flavor.hqdmod." + flavor.getFlavorId()).withStyle(ChatFormatting.WHITE)
        ).withStyle(ChatFormatting.GOLD));

        ChatFormatting chargeColor = charge > (max / 2) ? ChatFormatting.GREEN :
                (charge > 0 ? ChatFormatting.YELLOW : ChatFormatting.RED);
        tooltip.add(Component.translatable("tooltip.hqdmod.charge",
                Component.literal(charge + "/" + max).withStyle(chargeColor)
        ).withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.hqdmod.effects_header").withStyle(ChatFormatting.BLUE));

        MutableComponent eff1Name = Component.translatable(flavor.getEffect1().get().value().getDescriptionId());
        String amp1 = flavor.getAmplifier1() > 0 ? " II" : " I";
        tooltip.add(Component.literal(" - ").append(eff1Name).append(amp1)
                .append(" (" + durationSec + " ")
                .append(Component.translatable("tooltip.hqdmod.seconds"))
                .append(")")
                .withStyle(ChatFormatting.AQUA));

        MutableComponent eff2Name = Component.translatable(flavor.getEffect2().get().value().getDescriptionId());
        String amp2 = flavor.getAmplifier2() > 0 ? " II" : " I";
        tooltip.add(Component.literal(" - ").append(eff2Name).append(amp2)
                .append(" (" + durationSec + " ")
                .append(Component.translatable("tooltip.hqdmod.seconds"))
                .append(")")
                .withStyle(ChatFormatting.AQUA));

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.hqdmod.recharge_hint").withStyle(ChatFormatting.DARK_GRAY));
    }
}
