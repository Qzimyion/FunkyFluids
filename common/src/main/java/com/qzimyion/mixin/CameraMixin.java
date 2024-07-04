package com.qzimyion.mixin;

import com.qzimyion.registry.BlockRegistry;
import net.minecraft.client.Camera;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow private BlockGetter level;

    @Shadow public abstract Camera.NearPlane getNearPlane();

    @Shadow private Vec3 position;

    @Inject(method = "getFluidInCamera", at = @At("HEAD"), cancellable = true)
    private void newFog(CallbackInfoReturnable<FogType> cir){
        Camera.NearPlane nearPlane = this.getNearPlane();
        List<Vec3> list = Arrays.asList(nearPlane.forward, nearPlane.getTopLeft(), nearPlane.getTopRight(), nearPlane.getBottomLeft(), nearPlane.getBottomRight());
        for (Vec3 vec3 : list){
            Vec3 vec32 = position.add(vec3);
            BlockPos blockPos = BlockPos.containing(vec32);
            BlockState blockState = this.level.getBlockState(blockPos);
            if (blockState.is(BlockRegistry.CLOUD_BLOCK) || blockState.is(BlockRegistry.COMPRESSED_CLOUD_BLOCK) || blockState.is(BlockRegistry.ECTOPLASM_BLOCK)){
                cir.setReturnValue(FogType.POWDER_SNOW);
            }
        }
    }
}
