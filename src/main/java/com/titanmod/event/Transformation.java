package com.titanmod.event;

import com.titanmod.item.GiantSerumItem;
import com.titanmod.registry.ModEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.phys.AABB;

public final class Transformation {
    public static final float WIDTH = 4.0f, HEIGHT = 15.0f, EYE = 13.6f;
    private static final String CD_KEY = "titanmod_cd";

    private Transformation() {}

    public static void toggle(ServerPlayer p) {
        if (!p.hasEffect(ModEffects.GIANT_POWER.get())) {
            p.displayClientMessage(new TranslatableComponent("msg.titanmod.no_power"), true);
            return;
        }
        long now = p.getLevel().getGameTime();
        if (now < p.getPersistentData().getLong(CD_KEY)) {
            p.displayClientMessage(new TranslatableComponent("msg.titanmod.cooldown"), true);
            return;
        }
        p.getPersistentData().putLong(CD_KEY, now + 40);
        if (p.hasEffect(ModEffects.GIANT_FORM.get())) revert(p); else transform(p);
    }

    private static void transform(ServerPlayer p) {
        if (p.isPassenger() || p.isSpectator()) return;
        ServerLevel level = p.getLevel();
        AABB box = EntityDimensions.scalable(WIDTH, HEIGHT).makeBoundingBox(p.position());
        if (!level.noCollision(p, box)) {
            p.displayClientMessage(new TranslatableComponent("msg.titanmod.no_space"), true);
            return;
        }
        p.addEffect(new MobEffectInstance(ModEffects.GIANT_FORM.get(), GiantSerumItem.FOREVER, 0, false, false, true));
        p.setHealth(p.getMaxHealth());

        double x = p.getX(), y = p.getY(), z = p.getZ();
        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
        if (bolt != null) {
            bolt.moveTo(x, y, z);
            bolt.setVisualOnly(true);
            level.addFreshEntity(bolt);
        }
        level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, x, y + 1, z, 1, 0, 0, 0, 0);
        level.sendParticles(ParticleTypes.CLOUD, x, y + 1, z, 150, 2, 4, 2, 0.2);
        level.sendParticles(ParticleTypes.ELECTRIC_SPARK, x, y + 2, z, 120, 2, 5, 2, 0.4);
        // the roar
        level.playSound(null, p.blockPosition(), SoundEvents.RAVAGER_ROAR, SoundSource.PLAYERS, 6.0f, 0.5f);
        level.playSound(null, p.blockPosition(), SoundEvents.ENDER_DRAGON_GROWL, SoundSource.PLAYERS, 6.0f, 0.5f);
    }

    private static void revert(ServerPlayer p) {
        p.removeEffect(ModEffects.GIANT_FORM.get());
        ServerLevel level = p.getLevel();
        level.sendParticles(ParticleTypes.CLOUD, p.getX(), p.getY() + 1, p.getZ(), 80, 1, 2, 1, 0.1);
        level.playSound(null, p.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 3.0f, 1.2f);
    }
}
