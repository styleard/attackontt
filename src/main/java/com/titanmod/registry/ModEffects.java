package com.titanmod.registry;

import com.titanmod.TitanMod;
import com.titanmod.effect.GiantFormEffect;
import com.titanmod.effect.GiantPowerEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TitanMod.MODID);

    public static final RegistryObject<MobEffect> GIANT_POWER = EFFECTS.register("giant_power", GiantPowerEffect::new);
    public static final RegistryObject<MobEffect> GIANT_FORM = EFFECTS.register("giant_form", GiantFormEffect::new);
}
