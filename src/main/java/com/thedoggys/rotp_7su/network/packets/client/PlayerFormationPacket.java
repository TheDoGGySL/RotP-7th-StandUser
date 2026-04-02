package com.thedoggys.rotp_7su.network.packets.client;

import com.github.standobyte.jojo.network.packets.IModPacketHandler;
import com.thedoggys.rotp_7su.capability.LivingDataProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerFormationPacket {
    private final int formation;

    public PlayerFormationPacket(int unitType){
        this.formation = unitType;
    }

    public static class Handler implements IModPacketHandler<PlayerFormationPacket> {
        @Override
        public void encode(PlayerFormationPacket msg, PacketBuffer buf) {
            buf.writeInt(msg.formation);
        }


        @Override
        public PlayerFormationPacket decode(PacketBuffer buf) {
            int packetType = buf.readInt();
            return new PlayerFormationPacket(packetType);
        }
        @Override
        public void handle(PlayerFormationPacket msg, Supplier<NetworkEvent.Context> ctx) {
            NetworkEvent.Context context = ctx.get();
            context.enqueueWork(() -> {
                ServerPlayerEntity player = context.getSender();
                if (player != null) {
                    player.getCapability(LivingDataProvider.CAPABILITY).resolve().ifPresent(cap -> {
                        cap.setFormation(msg.formation);
                        System.out.println("Formation received on server: " + msg.formation);
                    });
                }
            });
            context.setPacketHandled(true);
        }
        @Override
        public Class<PlayerFormationPacket> getPacketClass() {
            return PlayerFormationPacket.class;
        }
    }
}
