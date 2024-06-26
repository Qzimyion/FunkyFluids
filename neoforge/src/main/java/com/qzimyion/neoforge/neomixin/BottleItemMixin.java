package com.qzimyion.neoforge.neomixin;

import com.qzimyion.registry.ItemRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BottleItem.class)
public class BottleItemMixin {

    @Inject(method = "use", at = @At("HEAD"))
    public void use(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir){
        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.is(Items.GLASS_BOTTLE.getDefaultInstance().getItem())){
            if (player.getY() >= 196 && player.getY() <= 198){
                heldItem.shrink(1);
                ItemStack newStack = ItemUtils.createFilledResult(heldItem, player , ItemRegistry.CLOUD_BOTTLE.get().getDefaultInstance(), false);
                player.setItemInHand(hand, newStack);
                player.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH);
            }
        }
    }

}
