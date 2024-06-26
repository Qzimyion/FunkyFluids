package com.qzimyion.items;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class MidAirPlacementBlockItem extends BlockItem  {

    public MidAirPlacementBlockItem(RegistrySupplier<Block> block, Properties properties) {
        super(block.get(), properties);
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
}
