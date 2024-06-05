package io.github.fadedphosphor.ingame;

import io.github.fadedphosphor.FadedPhosphor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FadedPhosphor.MODID);

    public static final RegistryObject<BlockEntityType<PhosphorBlockEntity>> PHOSPHOR_LANTERN_BLOCK_ENTITY =
        BLOCK_ENTITIES.register("phosphor_lantern_block_entity",
            () -> BlockEntityType.Builder.of(PhosphorBlockEntity::new, BlockInit.PHOSPHOR_LANTERN_BLOCK.get())
                    .build(null));
}
