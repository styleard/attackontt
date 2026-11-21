package com.titanmod.client;

import com.titanmod.TitanMod;
import com.titanmod.entity.WarriorGiantEntity;
import com.titanmod.network.ModNetwork;
import com.titanmod.network.TransformPacket;
import com.titanmod.registry.ModEffects;
import com.titanmod.registry.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = TitanMod.MODID, value = Dist.CLIENT)
public class ClientForgeEvents {

    private static class GiantView {
        WarriorGiantEntity dummy;
        boolean wasSwinging;
    }

    private static final Map<UUID, GiantView> VIEWS = new HashMap<>();

    private static boolean isGiant(Player p) {
        return p.hasEffect(ModEffects.GIANT_FORM.get());
    }

    /** Shift + M -> ask the server to transform / revert. */
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        while (Keys.TRANSFORM.consumeClick()) {
            ModNetwork.CHANNEL.sendToServer(new TransformPacket());
        }
    }

    /** Replace the player model with the animated Warrior Giant model. */
    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        Player p = event.getPlayer();
        if (!isGiant(p)) {
            VIEWS.remove(p.getUUID());
            return;
        }
        GiantView v = VIEWS.get(p.getUUID());
        if (v == null || v.dummy.level != p.level) {
            WarriorGiantEntity created = ModEntities.WARRIOR_GIANT.get().create(p.level);
            if (created == null) return;
            v = new GiantView();
            v.dummy = created;
            VIEWS.put(p.getUUID(), v);
            created.tickCount = p.tickCount;
            created.playAction("roar", 60); // mouth opens like a scream
        }
        WarriorGiantEntity d = v.dummy;
        d.setPos(p.getX(), p.getY(), p.getZ());
        d.xo = p.xo; d.yo = p.yo; d.zo = p.zo;
        d.xOld = p.xOld; d.yOld = p.yOld; d.zOld = p.zOld;
        d.yBodyRot = p.yBodyRot; d.yBodyRotO = p.yBodyRotO;
        d.yHeadRot = p.yHeadRot; d.yHeadRotO = p.yHeadRotO;
        d.setYRot(p.getYRot()); d.yRotO = p.yRotO;
        d.setXRot(p.getXRot()); d.xRotO = p.xRotO;
        d.tickCount = p.tickCount;
        d.hurtTime = p.hurtTime;
        d.deathTime = p.deathTime;
        d.animationSpeed = p.animationSpeed;
        d.animationSpeedOld = p.animationSpeedOld;
        d.animationPosition = p.animationPosition;

        boolean swinging = p.swinging;
        if (swinging && !v.wasSwinging) d.playAction("attack", 18);
        v.wasSwinging = swinging;

        event.setCanceled(true);
        float partial = event.getPartialRenderTick();
        Minecraft.getInstance().getEntityRenderDispatcher().render(d, 0, 0, 0,
                Mth.lerp(partial, p.yRotO, p.getYRot()), partial,
                event.getMatrixStack(), event.getBuffers(), event.getLight());
    }

    /** Hide the vanilla first-person arm while in giant form. */
    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        Player p = Minecraft.getInstance().player;
        if (p != null && isGiant(p)) event.setCanceled(true);
    }
}
