package com.qzimyion.fabric.datagen.lang;

import com.qzimyion.registry.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class En_Us extends FabricLanguageProvider {
    public En_Us(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ItemRegistry.CLOUD_BOTTLE.get(), "Cloud In A Bottle");
        translationBuilder.add(ItemRegistry.COMPRESSED_CLOUD_BLOCKITEM.get(), "Compressed Cloud Block");
        translationBuilder.add(ItemRegistry.ECTOPLASM_BOTTLE.get(), "Ectoplasm In A Bottle");
    }
}
