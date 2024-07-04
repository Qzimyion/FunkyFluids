package com.qzimyion.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class NonNewtonianBlock extends HalfTransparentBlock {

    public NonNewtonianBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter Level, @NotNull BlockPos Pos, @NotNull CollisionContext Context) {
        if (Context instanceof EntityCollisionContext entitycollisioncontext) {
            Entity entity = entitycollisioncontext.getEntity();
            if (entity != null) {
                boolean flag = entity instanceof FallingBlockEntity;
                if (flag || hasFeatherFallingIV((LivingEntity) entity) || entity.fallDistance <= 0 && entity.isSprinting() && Context.isAbove(Shapes.block(), Pos, false) && !Context.isDescending()) {
                    return Shapes.block();
                }
            }
        }
        return Shapes.empty();
    }


    @Override
    protected void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (entity instanceof LivingEntity){
            ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 10));
            if (entity.isSprinting()){
                Vec3 RunningSpeed = new Vec3(0.002, 0.001, 0.0025);
                entity.makeStuckInBlock(state, RunningSpeed);
            } else {
                Vec3 WalkSpeed = new Vec3(0.9f, 1.5, 0.9f);
                entity.makeStuckInBlock(state, WalkSpeed);
            }
            if (entity.isShiftKeyDown()){
                Vec3 CrouchSpeed = new Vec3(1.1, 1.1, 1.1);
                entity.makeStuckInBlock(state, CrouchSpeed);
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 20));
            }
        }
        super.entityInside(state, level, pos, entity);
    }

    public static boolean hasFeatherFallingIV(LivingEntity livingEntity) {
        Holder.Reference<Enchantment> entry = livingEntity.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(Enchantments.RESPIRATION).get();
        return EnchantmentHelper.getEnchantmentLevel(entry, livingEntity) == 4;
    }

    @Override
    protected @NotNull VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return Shapes.block();
    }
}
