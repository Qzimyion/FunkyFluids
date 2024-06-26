package com.qzimyion.fabric.client;

import com.qzimyion.registry.BlockRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public final class BottleCloudFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        RenderType renderType = RenderType.translucent();

        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CLOUD_BLOCK.get(), renderType);
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.COMPRESSED_CLOUD_BLOCK.get(), renderType);
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ECTOPLASM_BLOCK.get(), renderType);
    }
}
