package com.titanmod.client;

import com.titanmod.entity.MindlessGiantEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MindlessGiantRenderer extends GeoEntityRenderer<MindlessGiantEntity> {
    public MindlessGiantRenderer(EntityRendererProvider.Context context) {
        super(context, new GiantModel<>("mindless_giant")); // geo is already exported at 6 blocks
        this.shadowRadius = 1.5f;
    }
}
