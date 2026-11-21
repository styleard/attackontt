package com.titanmod.client;

import com.titanmod.TitanMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GiantModel<T extends IAnimatable> extends AnimatedGeoModel<T> {
    private final String name;

    public GiantModel(String name) { this.name = name; }

    @Override
    public ResourceLocation getModelLocation(T animatable) {
        return new ResourceLocation(TitanMod.MODID, "geo/" + name + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(T animatable) {
        return new ResourceLocation(TitanMod.MODID, "textures/entity/" + name + ".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(T animatable) {
        return new ResourceLocation(TitanMod.MODID, "animations/" + name + ".animation.json");
    }
}
