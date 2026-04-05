package com.thedoggys.rotp_7su.network;

import com.github.standobyte.jojo.network.packets.IModPacketHandler;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.network.packets.client.PlayerFormationPacket;
import com.thedoggys.rotp_7su.network.packets.server.MiraclesAura2Packet;
import com.thedoggys.rotp_7su.network.packets.server.MiraclesAuraPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;

public class AddonPackets {
    private static final String PROTOCOL_VERSION = "1";
    private static SimpleChannel channel;
    private static SimpleChannel clientChannel;
    private static int packetIndex = 0;


    public static void init() {
        channel = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(AddonMain.MOD_ID, "channel"))
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .simpleChannel();
        clientChannel = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(AddonMain.MOD_ID, "client_channel"))
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .simpleChannel();

        packetIndex = 0;

        registerMessage(clientChannel,new PlayerFormationPacket.Handler(), Optional.of(NetworkDirection.PLAY_TO_SERVER));
        channel.registerMessage(packetIndex++, MiraclesAuraPacket.class,
                MiraclesAuraPacket::encode,MiraclesAuraPacket::decode,MiraclesAuraPacket::handle);
        channel.registerMessage(packetIndex++, MiraclesAura2Packet.class,
                MiraclesAura2Packet::encode, MiraclesAura2Packet::decode,MiraclesAura2Packet::handle);

    }

    public static void sendToServer(Object msg) {
        clientChannel.sendToServer(msg);
    }

    public static void sendToClient(Object msg, ServerPlayerEntity player) {
        if (!(player instanceof FakePlayer)) {
            channel.send(PacketDistributor.PLAYER.with(() -> player), msg);
        }
    }

    public static void sendToClientsTracking(Object msg, Entity entity) {
        channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), msg);
    }

    public static void sendToClientsTrackingAndSelf(Object msg, Entity entity) {
        channel.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), msg);
    }

    private static <MSG> void registerMessage(SimpleChannel channel, IModPacketHandler<MSG> handler, Optional<NetworkDirection> networkDirection) {
        if (packetIndex > 127) {
            throw new IllegalStateException("Too many packets (> 127) registered for a single channel!");
        }
        channel.registerMessage(packetIndex++, handler.getPacketClass(), handler::encode, handler::decode, handler::enqueueHandleSetHandled, networkDirection);
    }
}
