package com.qzimyion.blocks;

import com.qzimyion.registry.BlockRegistry;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DecayingCloudBlock extends NonNewtonianBlock implements BucketPickup {
    public static final int STABILITY_MAX_DISTANCE = 7;
    public static final IntegerProperty DISTANCE = BlockStateProperties.STABILITY_DISTANCE;

    public DecayingCloudBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, 0));
    }

    @Override
    protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        int i = getDistance(serverLevel, blockPos);
        BlockState state = blockState.setValue(DISTANCE, i);
        if (blockState.getValue(DISTANCE) == STABILITY_MAX_DISTANCE) {
            serverLevel.destroyBlock(blockPos, true);
        } else if (state != blockState) {
            serverLevel.setBlock(blockPos, blockState, 3);
        }
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
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();
        int i = getDistance(level, blockPos);
        if (this.CloudDecay(context.getLevel(), context.getClickedPos())) {
            context.getLevel().scheduleTick(context.getClickedPos(), this,
                    100 + context.getLevel().getRandom().nextInt(80));
        }
        return this.defaultBlockState().setValue(DISTANCE, i);
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return getDistance(level, pos) < STABILITY_MAX_DISTANCE;
    }


    public static int getDistance(BlockGetter level, BlockPos pos) {
        BlockPos.MutableBlockPos mutableBlockPos = pos.mutable().move(Direction.DOWN);
        int i = STABILITY_MAX_DISTANCE;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockState2 = level.getBlockState(mutableBlockPos.setWithOffset(pos, direction));
            if (blockState2.is(BlockRegistry.CLOUD_BLOCK.get())) {
                i = Math.min(i, blockState2.getValue(DISTANCE) + 1);
                if (i == 1) {
                    break;
                }
            }
        }

        return i;
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
