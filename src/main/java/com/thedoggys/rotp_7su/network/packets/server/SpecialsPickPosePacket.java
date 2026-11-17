package com.thedoggys.rotp_7su.network.packets.server;

import com.github.standobyte.jojo.client.ClientUtil;
import com.thedoggys.rotp_7su.entity.SpecialsEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SpecialsPickPosePacket {
    private final int entityID;

    public SpecialsPickPosePacket(int entityID){
        this.entityID = entityID;
    }


    public static void encode(SpecialsPickPosePacket msg, PacketBuffer buf) {
        buf.writeInt(msg.entityID);
    }

    public static SpecialsPickPosePacket decode(PacketBuffer buf) {
        return new SpecialsPickPosePacket(buf.readInt());
    }


    public static void handle(SpecialsPickPosePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = ClientUtil.getEntityById(msg.entityID);
            if (entity instanceof SpecialsEntity && !((SpecialsEntity) entity).getCurrentTask().isPresent()) {
                ((SpecialsEntity) entity).setStandPose(SpecialsEntity.PICK_POSE);
                ((SpecialsEntity) entity).pickPoseTick = entity.tickCount;
            }
        });
        ctx.get().setPacketHandled(true);

    }

}
