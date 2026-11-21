package com.titanmod.registry;

import com.titanmod.TitanMod;
import com.titanmod.item.GiantSerumItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TitanMod.MODID);

    public static final RegistryObject<Item> GIANT_SERUM = ITEMS.register("giant_serum", GiantSerumItem::new);
    public static final RegistryObject<Item> MINDLESS_GIANT_SPAWN_EGG = ITEMS.register("mindless_giant_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.MINDLESS_GIANT, 0x9B6B4F, 0x2A1A10,
                    new Item.Properties().tab(ModTabs.TAB)));
}
