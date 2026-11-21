package com.titanmod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/** Shown in the effect list after drinking the serum. It is the ability to transform. */
public class GiantPowerEffect extends MobEffect {
    public GiantPowerEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x3CFF6B);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>(); // milk cannot remove it
    }
}
