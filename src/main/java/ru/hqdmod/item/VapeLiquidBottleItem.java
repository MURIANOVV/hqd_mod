package ru.hqdmod.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import ru.hqdmod.config.ModConfig;

import java.util.List;

public class VapeLiquidBottleItem extends Item {
    public VapeLiquidBottleItem(Properties properties) {
        super(properties.stacksTo(16).craftRemainder(Items.GLASS_BOTTLE));
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        if (action == ClickAction.SECONDARY) {
            ItemStack slotStack = slot.getItem();
            if (slotStack.getItem() instanceof HQDItem hqdItem) {
                int currentCharge = hqdItem.getCharge(slotStack);
                int maxCharge = hqdItem.getMaxCharge();

                if (currentCharge < maxCharge) {
                    int restored = Math.min(maxCharge, currentCharge + ModConfig.CONFIG.getRechargeAmount());
                    hqdItem.setCharge(slotStack, restored);

                    stack.shrink(1);
                    ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
                    if (!player.getInventory().add(bottle)) {
                        player.drop(bottle, false);
                    }

                    player.playSound(SoundEvents.BOTTLE_EMPTY, 0.8F, 1.2F);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        InteractionHand otherHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack otherStack = player.getItemInHand(otherHand);

        if (otherStack.getItem() instanceof HQDItem hqdItem) {
            int currentCharge = hqdItem.getCharge(otherStack);
            int maxCharge = hqdItem.getMaxCharge();

            if (currentCharge < maxCharge) {
                if (!level.isClientSide()) {
                    int restored = Math.min(maxCharge, currentCharge + ModConfig.CONFIG.getRechargeAmount());
                    hqdItem.setCharge(otherStack, restored);

                    stack.shrink(1);
                    ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
                    if (!player.getInventory().add(bottle)) {
                        player.drop(bottle, false);
                    }

                    player.displayClientMessage(
                            Component.translatable("message.hqdmod.recharged", restored, maxCharge).withStyle(ChatFormatting.GREEN),
                            true
                    );
                }

                level.playSound(player, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS, 0.8F, 1.2F);
                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
            } else {
                if (!level.isClientSide()) {
                    player.displayClientMessage(
                            Component.translatable("message.hqdmod.already_full").withStyle(ChatFormatting.YELLOW),
                            true
                    );
                }
                return InteractionResultHolder.fail(stack);
            }
        }

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        int restore = ModConfig.CONFIG.getRechargeAmount();
        tooltip.add(Component.translatable("tooltip.hqdmod.vape_liquid_desc", restore).withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.translatable("tooltip.hqdmod.vape_liquid_how_to").withStyle(ChatFormatting.GRAY));
    }
}
