package com.qzimyion.fabric.datagen;

import com.qzimyion.registry.BlockRegistry;
import com.qzimyion.registry.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;

import java.util.concurrent.CompletableFuture;

public class RecipeDatagen extends FabricRecipeProvider {

    public RecipeDatagen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput exporter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, BlockRegistry.COMPRESSED_CLOUD_BLOCK.get(), 4)
                .requires(ItemRegistry.CLOUD_BOTTLE.get(), 4).unlockedBy("has_cloud_bottle",
                        VanillaRecipeProvider.has(ItemRegistry.CLOUD_BOTTLE.get())).save(exporter);
    }
}
