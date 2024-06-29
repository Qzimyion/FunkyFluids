package com.qzimyion.registry;

import com.qzimyion.items.MidAirBucketItem;
import com.qzimyion.items.MidAirPlacementBlockItem;
import com.qzimyion.items.MidAirScaffoldItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;

import static com.qzimyion.BottleInACloudModMain.MOD_ID;
import static net.minecraft.world.item.Items.GLASS_BOTTLE;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);

    //==Items==//
    public static final RegistrySupplier<Item> CLOUD_BOTTLE = ITEMS.register("cloud_in_bottle",
            ()-> new MidAirScaffoldItem(BlockRegistry.CLOUD_BLOCK, new Item.Properties().craftRemainder(GLASS_BOTTLE).stacksTo(32), SoundEvents.BOTTLE_FILL_DRAGONBREATH));


    public static final RegistrySupplier<Item> COMPRESSED_CLOUD_BLOCKITEM = ITEMS.register("compressed_cloud_block",
            ()-> new MidAirPlacementBlockItem(BlockRegistry.COMPRESSED_CLOUD_BLOCK, new Item.Properties()));


    public static final RegistrySupplier<Item> ECTOPLASM_BOTTLE = ITEMS.register("soul_bottle",
            ()-> new MidAirBucketItem(BlockRegistry.ECTOPLASM_BLOCK, new Item.Properties().stacksTo(32), SoundEvents.BOTTLE_FILL_DRAGONBREATH));

    public static void registerItems(){
        ITEMS.register();
    }
}
