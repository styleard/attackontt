package com.titanmod.registry;

import com.titanmod.TitanMod;
import com.titanmod.entity.MindlessGiantEntity;
import com.titanmod.entity.WarriorGiantEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, TitanMod.MODID);

    public static final RegistryObject<EntityType<MindlessGiantEntity>> MINDLESS_GIANT = ENTITIES.register("mindless_giant",
            () -> EntityType.Builder.of(MindlessGiantEntity::new, MobCategory.MONSTER)
                    .sized(2.0f, 6.0f).clientTrackingRange(16).build("mindless_giant"));

    // Only used to render the player while transformed (never added to the world).
    public static final RegistryObject<EntityType<WarriorGiantEntity>> WARRIOR_GIANT = ENTITIES.register("warrior_giant",
            () -> EntityType.Builder.of(WarriorGiantEntity::new, MobCategory.MISC)
                    .sized(4.0f, 15.0f).noSummon().noSave().fireImmune().build("warrior_giant"));
}
