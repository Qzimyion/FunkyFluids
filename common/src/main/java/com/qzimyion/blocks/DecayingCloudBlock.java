package com.qzimyion.blocks;

import com.qzimyion.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DecayingCloudBlock extends NonNewtonianBlock implements BucketPickup {

    public DecayingCloudBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (this.CloudDecay(serverLevel, blockPos)) {
            serverLevel.setBlock(blockPos, VaporisesInto(), 2);
        }
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (this.CloudDecay(levelAccessor, blockPos)) {
            levelAccessor.scheduleTick(blockPos, this, 100 + levelAccessor.getRandom().nextInt(80));
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    public static BlockState VaporisesInto() {
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        if (this.CloudDecay(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos())) {
            blockPlaceContext.getLevel().scheduleTick(blockPlaceContext.getClickedPos(), this,
                    100 + blockPlaceContext.getLevel().getRandom().nextInt(80));
        }
        return this.defaultBlockState();
    }

    protected boolean CloudDecay(BlockGetter blockGetter, BlockPos blockPos) {
        for (Direction direction : Direction.values()) {
            FluidState fluidState = blockGetter.getFluidState(blockPos.relative(direction));
            if (!fluidState.is(FluidTags.WATER)) continue; //Basically copied from the coral block
            return false;
        }
        return true;
    }

    @Override
    public @NotNull ItemStack pickupBlock(@Nullable Player player, LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        levelAccessor.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 11);
        if (!levelAccessor.isClientSide()) {
            levelAccessor.levelEvent(2001, blockPos, Block.getId(blockState));
        }
        return new ItemStack(ItemRegistry.CLOUD_BOTTLE);
    }

    @Override
    public @NotNull Optional<SoundEvent> getPickupSound() {
        return Optional.empty();
    }
}
