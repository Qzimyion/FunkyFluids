package com.qzimyion.fabric;

import net.fabricmc.api.ModInitializer;

import com.qzimyion.BottleInACloudModMain;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

public final class BottleInACloudModFabric implements ModInitializer {
    @Override
    public void onInitialize() {

        CloudEventFabric.registerFabricEvent();
        CreativeMenuRegistryFabric.registerItemGroups();
        BottleInACloudModMain.init();
    }
}
