package com.qzimyion.fabric.datagen;

import com.qzimyion.registry.BlockRegistry;
import com.qzimyion.registry.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;

public class ModelDatagen extends FabricModelProvider {
    public ModelDatagen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        blockStateModelGenerator.createTrivialCube(BlockRegistry.CLOUD_BLOCK.get());
        blockStateModelGenerator.createTrivialCube(BlockRegistry.COMPRESSED_CLOUD_BLOCK.get());
        blockStateModelGenerator.createTrivialCube(BlockRegistry.ECTOPLASM_BLOCK.get());
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(ItemRegistry.CLOUD_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
        //itemModelGenerator.generateFlatItem(ItemRegistry.COMPRESSED_CLOUD_BLOCKITEM.get(), ModelTemplates.CUBE);
        itemModelGenerator.generateFlatItem(ItemRegistry.ECTOPLASM_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
    }
}
