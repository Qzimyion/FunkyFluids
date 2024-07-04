package com.qzimyion.blocks;

import com.qzimyion.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.effects.PlaySoundEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class EctoplasmBlock extends NonNewtonianBlock implements BucketPickup {

    public EctoplasmBlock(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player,
                                              InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (itemStack.getItem() instanceof BlockItem bi) {
            Block block = bi.getBlock();

            UseOnContext context = new UseOnContext(player, interactionHand, new BlockHitResult(new Vec3(0.5F, 1F, 0.5F), blockHitResult.getDirection(), blockPos, false));
            BlockPlaceContext blockContext = new BlockPlaceContext(context);

            BlockPos targetPos = blockHitResult.getBlockPos();
            BlockState stateToPlace = block.getStateForPlacement(blockContext);
            if (stateToPlace != null && stateToPlace.canSurvive(level, blockPos)) {
                level.setBlockAndUpdate(blockPos, stateToPlace);
                new PlaySoundEffect(SoundEvents.SOUL_ESCAPE,  ConstantFloat.of(0.6F), UniformFloat.of(0.6F, 1.0F));
                level.playSound(player, blockPos, stateToPlace.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1F, 1F);
                ParticleUtils.spawnParticles(level, targetPos, 3*2, 3.0, 3.0, true, ParticleTypes.SOUL);
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public @NotNull ItemStack pickupBlock(@Nullable Player player, LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        levelAccessor.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 11);
        if (!levelAccessor.isClientSide()) {
            levelAccessor.levelEvent(2001, blockPos, Block.getId(blockState));
        }
        return new ItemStack(ItemRegistry.ECTOPLASM_BOTTLE);
    }

    @Override
    public @NotNull Optional<SoundEvent> getPickupSound() {
        return Optional.empty();
    }
}
