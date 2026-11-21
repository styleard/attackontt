package com.titanmod.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTabs {
    public static final CreativeModeTab TAB = new CreativeModeTab("titanmod") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.GIANT_SERUM.get());
        }
    };
}
