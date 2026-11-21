package com.titanmod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Active while the player is in giant form. Gives the stats; size/render are handled by events. */
public class GiantFormEffect extends MobEffect {
    private static final UUID U_REACH = UUID.fromString("5b0c1d34-7a11-4c0e-9a51-0f2f6d7a1001");

    public GiantFormEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xE0963C);
        addAttributeModifier(Attributes.MAX_HEALTH, "5b0c1d34-7a11-4c0e-9a51-0f2f6d7a1004", 60.0, AttributeModifier.Operation.ADDITION);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, "5b0c1d34-7a11-4c0e-9a51-0f2f6d7a1005", 25.0, AttributeModifier.Operation.ADDITION);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "5b0c1d34-7a11-4c0e-9a51-0f2f6d7a1006", 0.8, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, "5b0c1d34-7a11-4c0e-9a51-0f2f6d7a1007", 1.0, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap map, int amplifier) {
        super.addAttributeModifiers(entity, map, amplifier);
        AttributeInstance reach = map.getInstance(ForgeMod.REACH_DISTANCE.get());
        if (reach != null && reach.getModifier(U_REACH) == null) {
            reach.addTransientModifier(new AttributeModifier(U_REACH, "titanmod_giant_reach", 9.0, AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap map, int amplifier) {
        super.removeAttributeModifiers(entity, map, amplifier);
        AttributeInstance reach = map.getInstance(ForgeMod.REACH_DISTANCE.get());
        if (reach != null) reach.removeModifier(U_REACH);
        if (entity.getHealth() > entity.getMaxHealth()) entity.setHealth(entity.getMaxHealth());
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }
}
