package com.titanmod.client;

import com.titanmod.entity.WarriorGiantEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WarriorGiantRenderer extends GeoEntityRenderer<WarriorGiantEntity> {
    public WarriorGiantRenderer(EntityRendererProvider.Context context) {
        super(context, new GiantModel<>("warrior_giant"));
        addLayer(new GlowLayer(this));
        this.shadowRadius = 3.0f;
    }
}
