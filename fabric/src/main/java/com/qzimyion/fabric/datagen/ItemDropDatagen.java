package com.qzimyion.fabric.datagen;

import com.qzimyion.registry.BlockRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;


public class ItemDropDatagen extends FabricBlockLootTableProvider {
    public ItemDropDatagen(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        add(BlockRegistry.COMPRESSED_CLOUD_BLOCK.get(), (Block block) -> createSingleItemTableWithSilkTouch(block, BlockRegistry.COMPRESSED_CLOUD_BLOCK.get()));
    }
}
