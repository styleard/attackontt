package com.titanmod.network;

import com.titanmod.event.Transformation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TransformPacket {
    public TransformPacket() {}

    public static void encode(TransformPacket msg, FriendlyByteBuf buf) {}

    public static TransformPacket decode(FriendlyByteBuf buf) { return new TransformPacket(); }

    public static void handle(TransformPacket msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context c = ctx.get();
        c.enqueueWork(() -> {
            ServerPlayer player = c.getSender();
            if (player != null) Transformation.toggle(player);
        });
        c.setPacketHandled(true);
    }
}
