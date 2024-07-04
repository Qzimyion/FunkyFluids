package com.qzimyion.registry;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.effects.PlaySoundEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class Events {

    public static void registerEvents(){
        InteractionEvent.RIGHT_CLICK_BLOCK.register((Player, InteractionHand, BlockPos, Direction)-> {
            BlockHitResult hitResult = new BlockHitResult(BlockPos.getCenter(), Direction.getOpposite(), BlockPos, false);
            Level level = Player.level();
            BlockPos targetPos = hitResult.getBlockPos();
            BlockState targetBlock = level.getBlockState(targetPos);
            ItemStack heldItem = Player.getItemInHand(InteractionHand);
            Random random = new Random();

            //Soul bottle and Soul sand to soul conversion
            if (heldItem.is(ItemRegistry.ECTOPLASM_BOTTLE.get().getDefaultInstance().getItem())){
                if (targetBlock.is(Blocks.SOUL_SAND)){
                    heldItem.shrink(1);
                    ItemStack newItem = ItemUtils.createFilledResult(heldItem, Player, ItemRegistry.ECTOPLASM_BOTTLE.get().getDefaultInstance(), false);
                    Player.setItemInHand(InteractionHand, newItem);
                    ParticleUtils.spawnParticles(level, targetPos, 3*2, 3.0, 3.0, true, ParticleTypes.SOUL);
                    Player.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH);
                    new PlaySoundEffect(SoundEvents.SOUL_ESCAPE,  ConstantFloat.of(0.6F), UniformFloat.of(0.6F, 1.0F));
                    if (random.nextFloat() <= 0.1f){
                        level.setBlock(targetPos, Blocks.SOUL_SOIL.defaultBlockState(), 2);
                    }
                }
            }
            //Take back ectoplasm
            if (heldItem.is(Items.GLASS_BOTTLE)){
                if (targetBlock.is(BlockRegistry.ECTOPLASM_BLOCK)){
                    heldItem.shrink(1);
                    ItemStack newItem = ItemUtils.createFilledResult(heldItem, Player, ItemRegistry.ECTOPLASM_BOTTLE.get().getDefaultInstance(), false);
                    Player.setItemInHand(InteractionHand, newItem);
                    Player.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH);
                    new PlaySoundEffect(SoundEvents.SOUL_ESCAPE,  ConstantFloat.of(0.6F), UniformFloat.of(0.6F, 1.0F));
                    level.setBlock(targetPos, Blocks.AIR.defaultBlockState(), 2);
                }
            }
            //Take back cloud block
            if (heldItem.is(Items.GLASS_BOTTLE)){
                if (targetBlock.is(BlockRegistry.CLOUD_BLOCK)){
                    heldItem.shrink(1);
                    ItemStack newItem = ItemUtils.createFilledResult(heldItem, Player, ItemRegistry.CLOUD_BOTTLE.get().getDefaultInstance(), false);
                    Player.setItemInHand(InteractionHand, newItem);
                    Player.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH);
                    level.setBlock(targetPos, Blocks.AIR.defaultBlockState(), 2);
                }
            }
        return EventResult.pass();
        });
    }
}
