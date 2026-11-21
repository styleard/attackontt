package com.titanmod.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.titanmod.TitanMod;
import com.titanmod.entity.WarriorGiantEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

/** Draws the glowing green eyes (full bright) using warrior_giant_glowmask.png. */
public class GlowLayer extends GeoLayerRenderer<WarriorGiantEntity> {
    private static final ResourceLocation GLOW = new ResourceLocation(TitanMod.MODID, "textures/entity/warrior_giant_glowmask.png");
    private static final ResourceLocation MODEL = new ResourceLocation(TitanMod.MODID, "geo/warrior_giant.geo.json");

    public GlowLayer(IGeoRenderer<WarriorGiantEntity> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int packedLight, WarriorGiantEntity entity,
                       float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
                       float netHeadYaw, float headPitch) {
        RenderType type = RenderType.eyes(GLOW);
        this.getRenderer().render(this.getEntityModel().getModel(MODEL), entity, partialTicks, type, stack, buffer,
                buffer.getBuffer(type), 15728640, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
    }
}
