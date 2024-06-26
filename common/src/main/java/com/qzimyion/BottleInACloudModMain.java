package com.qzimyion;

import com.qzimyion.registry.BlockRegistry;
import com.qzimyion.registry.Events;
import com.qzimyion.registry.ItemRegistry;

public final class BottleInACloudModMain {

    public static final String MOD_ID = "bottleinacloud";

    public static void init() {
        BlockRegistry.registerModBlocks();
        ItemRegistry.registerItems();
        Events.registerEvents();
    }
}
