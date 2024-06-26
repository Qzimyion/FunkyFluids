package com.qzimyion.fabric;

import com.qzimyion.fabric.datagen.ItemDropDatagen;
import com.qzimyion.fabric.datagen.ModelDatagen;
import com.qzimyion.fabric.datagen.RecipeDatagen;
import com.qzimyion.fabric.datagen.lang.En_Us;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DatagenFabric implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModelDatagen::new);
        pack.addProvider(En_Us::new);
        pack.addProvider(ItemDropDatagen::new);
        pack.addProvider(RecipeDatagen::new);
    }
}
