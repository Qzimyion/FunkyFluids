package com.qzimyion.neoforge;

import com.qzimyion.registry.ItemRegistry;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import com.qzimyion.BottleInACloudModMain;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(BottleInACloudModMain.MOD_ID)
public class BottleInACloudNeoForge {

    public BottleInACloudNeoForge(IEventBus modEventBus) {
        BottleInACloudModMain.init();
        modEventBus.addListener(this::buildContents);
    }

    @SubscribeEvent
    public void buildContents(BuildCreativeModeTabContentsEvent event){
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES){
            event.accept(ItemRegistry.CLOUD_BOTTLE.get());
            event.accept(ItemRegistry.COMPRESSED_CLOUD_BLOCKITEM.get());
            event.accept(ItemRegistry.ECTOPLASM_BOTTLE.get());
        }
    }
}
