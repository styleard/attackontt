package com.titanmod.event;

import com.titanmod.TitanMod;
import com.titanmod.item.GiantSerumItem;
import com.titanmod.registry.ModEffects;
import com.titanmod.registry.ModEntities;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = TitanMod.MODID)
public class CommonEvents {

    private static boolean isGiant(Player p) {
        return ModEffects.GIANT_FORM.isPresent() && p.hasEffect(ModEffects.GIANT_FORM.get());
    }

    /** Makes the player hitbox 4x15 while transformed. */
    @SubscribeEvent
    public static void onSize(EntityEvent.Size event) {
        if (event.getEntity() instanceof Player p && isGiant(p)) {
            event.setNewSize(EntityDimensions.scalable(Transformation.WIDTH, Transformation.HEIGHT));
            event.setNewEyeHeight(Transformation.EYE);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player p = event.player;
        boolean giant = isGiant(p);
        boolean big = p.getBbHeight() > 10.0f;
        if (giant != big) p.refreshDimensions();
        if (giant) p.maxUpStep = 2.5f;
        else if (p.maxUpStep == 2.5f) p.maxUpStep = 0.6f;
    }

    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player p && isGiant(p)) event.setDamageMultiplier(0f);
    }

    /** Giant punches also hit nearby entities. */
    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event) {
        Player p = event.getPlayer();
        if (p.level.isClientSide || !isGiant(p)) return;
        var target = event.getTarget();
        List<LivingEntity> near = p.level.getEntitiesOfClass(LivingEntity.class,
                target.getBoundingBox().inflate(4.0), e -> e != p && e != target && e.isAlive());
        for (LivingEntity e : near) e.hurt(DamageSource.playerAttack(p), 10.0f);
    }

    /** Keep the serum ability after death. */
    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;
        if (event.getOriginal().getPersistentData().getBoolean("titanmod_power")) {
            event.getPlayer().getPersistentData().putBoolean("titanmod_power", true);
        }
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player p = event.getPlayer();
        if (p.getPersistentData().getBoolean("titanmod_power") && !p.hasEffect(ModEffects.GIANT_POWER.get())) {
            p.addEffect(new MobEffectInstance(ModEffects.GIANT_POWER.get(), GiantSerumItem.FOREVER, 0, false, true, true));
        }
    }

    /** Natural spawning of the Mindless Giant in most overworld biomes. */
    @SubscribeEvent
    public static void onBiome(BiomeLoadingEvent event) {
        Biome.BiomeCategory cat = event.getCategory();
        switch (cat) {
            case PLAINS, FOREST, TAIGA, SAVANNA, DESERT, EXTREME_HILLS, JUNGLE, ICY, MESA, SWAMP ->
                    event.getSpawns().addSpawn(MobCategory.MONSTER,
                            new MobSpawnSettings.SpawnerData(ModEntities.MINDLESS_GIANT.get(), 2, 1, 1));
            default -> { }
        }
    }
}
