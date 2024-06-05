package io.github.fadedphosphor.ingame;

import io.github.fadedphosphor.FadedPhosphor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.block.SoundType;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FadedPhosphor.MODID);
    public static final RegistryObject<PhosphorBlock> PHOSPHOR_LANTERN_BLOCK = BLOCKS.register("phosphor_lantern_block_entity",
        () -> new PhosphorBlock(Properties.of()
            .mapColor(MapColor.METAL)
            .forceSolidOn()
            .strength(1F)
            .sound(SoundType.LANTERN)
            .lightLevel((state) -> 0)
            .noOcclusion()));
}