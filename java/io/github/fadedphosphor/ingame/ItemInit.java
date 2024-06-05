package io.github.fadedphosphor.ingame;

import io.github.fadedphosphor.FadedPhosphor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FadedPhosphor.MODID);

    public static final RegistryObject<Item> PHOSPHATE_ITEM = ITEMS.register("phosphate_bottle",
    () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PHOSPHOR_ITEM = ITEMS.register("phosphor_bottle",
    () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PHOSPHOR_LANTERN_BLOCK_ITEM = ITEMS.register("phosphor_lantern",
    () -> new BlockItem(BlockInit.PHOSPHOR_LANTERN_BLOCK.get(), new Item.Properties()));
}
