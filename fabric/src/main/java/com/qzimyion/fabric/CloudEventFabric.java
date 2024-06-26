package com.qzimyion.fabric;

import com.qzimyion.registry.ItemRegistry;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;

public class CloudEventFabric {

    public static void registerFabricEvent() {
        UseItemCallback.EVENT.register((player, level, hand) -> {
            ItemStack heldItem = player.getItemInHand(hand);
            if (heldItem.is(Items.GLASS_BOTTLE.getDefaultInstance().getItem())){
                if (player.getY() >= 196 && player.getY() <= 198){
                    heldItem.shrink(1);
                    ItemStack newStack = ItemUtils.createFilledResult(heldItem, player ,
                            ItemRegistry.CLOUD_BOTTLE.get().getDefaultInstance(), false);
                    player.setItemInHand(hand, newStack);
                    player.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH);
                }
                return InteractionResultHolder.consume(heldItem);
            }
            return InteractionResultHolder.pass(heldItem);
        });
    }
}
