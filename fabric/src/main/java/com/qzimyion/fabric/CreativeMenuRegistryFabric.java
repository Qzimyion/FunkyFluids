package com.qzimyion.fabric;

import com.qzimyion.registry.ItemRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

public class CreativeMenuRegistryFabric {

    public static void registerItemGroups(){
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(content -> {
            content.addAfter(Items.MILK_BUCKET, ItemRegistry.CLOUD_BOTTLE.get(),
                    ItemRegistry.COMPRESSED_CLOUD_BLOCKITEM.get(), ItemRegistry.ECTOPLASM_BOTTLE.get());
        });
    }
}
