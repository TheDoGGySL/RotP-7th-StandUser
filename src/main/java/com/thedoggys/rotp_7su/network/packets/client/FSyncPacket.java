package com.thedoggys.rotp_7su.network.packets.client;

import com.github.standobyte.jojo.network.packets.IModPacketHandler;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.thedoggys.rotp_7su.capability.LivingDataProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class FSyncPacket {
    private final int entityId;
    private final int act;

    public FSyncPacket(int entityId, int act){
        this.entityId = entityId;
        this.act = act;
    }

    public static class Handler implements IModPacketHandler<FSyncPacket> {
        public void encode(FSyncPacket msg, PacketBuffer buf) {
            buf.writeInt(msg.entityId);
            buf.writeInt(msg.act);
        }
        @Override
        public FSyncPacket decode(PacketBuffer buf) {
            return new FSyncPacket(buf.readInt(), buf.readInt());
        }
        @Override
        public void handle(FSyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ServerPlayerEntity player = ctx.get().getSender();
            player.getCapability(LivingDataProvider.CAPABILITY).ifPresent(nailCapability -> {
                IStandPower power = IStandPower.getPlayerStandPower(player);
                power.getType().unsummon(player, power);
                nailCapability.setShouldChangeFormation(true);
                nailCapability.setFormation(msg.act);
            });
        }
        @Override
        public Class<FSyncPacket> getPacketClass() {
            return FSyncPacket.class;
        }
    }
}
