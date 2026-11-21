package com.titanmod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

/** Visual stand-in for a transformed player (client side only, never spawned in the world). */
public class WarriorGiantEntity extends PathfinderMob implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private String action = null;
    private int actionEnd = 0;
    private boolean restart = false;

    public WarriorGiantEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0);
    }

    /** Plays a one-shot animation ("roar" or "attack") for the given number of ticks. */
    public void playAction(String name, int durationTicks) {
        this.action = name;
        this.actionEnd = this.tickCount + durationTicks;
        this.restart = true;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationController<?> c = event.getController();
        if (restart) { c.markNeedsReload(); restart = false; }
        if (action != null && this.tickCount < actionEnd) {
            c.setAnimation(new AnimationBuilder().addAnimation(action, false));
        } else if (event.isMoving()) {
            c.setAnimation(new AnimationBuilder().addAnimation("walk", true));
        } else {
            c.setAnimation(new AnimationBuilder().addAnimation("idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 5, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() { return factory; }
}
