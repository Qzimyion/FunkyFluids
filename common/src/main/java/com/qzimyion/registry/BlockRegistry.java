package com.qzimyion.registry;

import com.qzimyion.blocks.NonNewtonianBlock;
import com.qzimyion.blocks.DecayingCloudBlock;
import com.qzimyion.blocks.EctoplasmBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import static com.qzimyion.BottleInACloudModMain.MOD_ID;


public class BlockRegistry {


    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);

    //==Blocks==//
    public static final RegistrySupplier<Block> CLOUD_BLOCK = BLOCKS.register("cloud_block",
            ()-> new DecayingCloudBlock(BlockBehaviour.Properties.of().strength(0.02f).explosionResistance(0.0001f)
                    .sound(SoundType.WOOL).mapColor(DyeColor.LIGHT_BLUE).noOcclusion().pushReaction(PushReaction.DESTROY))
                    .defaultBlockState().getBlock());

    public static final RegistrySupplier<Block> COMPRESSED_CLOUD_BLOCK = BLOCKS.register("compressed_cloud_block",
            ()-> new NonNewtonianBlock(BlockBehaviour.Properties.ofFullCopy(CLOUD_BLOCK.get())).defaultBlockState().getBlock());

    public static final RegistrySupplier<Block> ECTOPLASM_BLOCK = BLOCKS.register("ectoplasm_block",
            ()-> new EctoplasmBlock(BlockBehaviour.Properties.ofFullCopy(CLOUD_BLOCK.get()).mapColor(DyeColor.CYAN))
                    .defaultBlockState().getBlock());

    public static void registerModBlocks() {
        BLOCKS.register();
    }
}
