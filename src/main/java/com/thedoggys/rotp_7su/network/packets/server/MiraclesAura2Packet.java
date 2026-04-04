package com.thedoggys.rotp_7su.network.packets.server;

import com.github.standobyte.jojo.client.ClientUtil;
import com.thedoggys.rotp_7su.capability.LivingData;
import com.thedoggys.rotp_7su.capability.LivingDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MiraclesAura2Packet {
    private final int entityID;
    private final boolean wipePresence;

    public MiraclesAura2Packet(int entityID, boolean stay){
        this.entityID = entityID;
        this.wipePresence = stay;
    }


    public static void encode(MiraclesAura2Packet msg, PacketBuffer buf) {
        buf.writeInt(msg.entityID);
        buf.writeBoolean(msg.wipePresence);
    }

    public static MiraclesAura2Packet decode(PacketBuffer buf) {
        return new MiraclesAura2Packet(buf.readInt(), buf.readBoolean());
    }


    public static void handle(MiraclesAura2Packet msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = ClientUtil.getEntityById(msg.entityID);
            if (entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                LazyOptional<LivingData> playerDataOptional = living.getCapability(LivingDataProvider.CAPABILITY);
                playerDataOptional.ifPresent(playerData ->{
                    playerData.setWipePresence(msg.wipePresence);
                });
            }
        });
        ctx.get().setPacketHandled(true);

    }
}