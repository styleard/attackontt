package com.titanmod.event;

import com.titanmod.TitanMod;
import com.titanmod.entity.MindlessGiantEntity;
import com.titanmod.entity.WarriorGiantEntity;
import com.titanmod.registry.ModEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TitanMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEvents {
    @SubscribeEvent
    public static void attributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.MINDLESS_GIANT.get(), MindlessGiantEntity.createAttributes().build());
        event.put(ModEntities.WARRIOR_GIANT.get(), WarriorGiantEntity.createAttributes().build());
    }
}
