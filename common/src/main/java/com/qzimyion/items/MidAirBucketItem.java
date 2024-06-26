package com.qzimyion.items;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("NullableProblems")
public class MidAirBucketItem extends SolidBucketItem implements DispensibleContainerItem {
    private final SoundEvent placeSound;

    public MidAirBucketItem(RegistrySupplier<Block> block, Properties properties, SoundEvent soundEvent) {
        super(block.get(), soundEvent, properties);
        this.placeSound = soundEvent;
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        double x = player.getX() + player.getLookAngle().x * 4.5;
        double y = player.getEyeY() + player.getLookAngle().y * 4.5;
        double z = player.getZ() + player.getLookAngle().z * 4.5;

        BlockPos pos = new BlockPos((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
        if (level.isInWorldBounds(pos) && level.getWorldBorder().isWithinBounds(pos) && level.getBlockState(pos).canBeReplaced()){
            BlockPlaceContext context = new BlockPlaceContext(level, player, hand, itemStack, new BlockHitResult(pos.getCenter(), Direction.NORTH, pos, false));
            InteractionResult result = useOn(context);
            if (result.consumesAction()){
                return InteractionResultHolder.sidedSuccess(itemStack, !level.isClientSide());
            }
        }
        return super.use(level, player, hand);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        InteractionResult interactionResult = super.useOn(useOnContext);
        Player player = useOnContext.getPlayer();
        if (interactionResult.consumesAction() && player != null && !player.isCreative()) {
            InteractionHand interactionHand = useOnContext.getHand();
            player.setItemInHand(interactionHand, Items.GLASS_BOTTLE.getDefaultInstance());
        }
        return interactionResult;
    }

    @Override
    protected SoundEvent getPlaceSound(BlockState blockState) {
        return this.placeSound;
    }

    @Override
    public boolean emptyContents(@Nullable Player player, Level level, BlockPos blockPos, @Nullable BlockHitResult blockHitResult) {
        if (level.isInWorldBounds(blockPos) && level.isEmptyBlock(blockPos)) {
            if (!level.isClientSide) {
                level.setBlock(blockPos, this.getBlock().defaultBlockState(), 3);
            }
            level.gameEvent(player, GameEvent.FLUID_PLACE, blockPos);
            level.playSound(player, blockPos, this.placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
            return true;
        }
        return false;
    }
}
