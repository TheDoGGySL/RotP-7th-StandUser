package com.thedoggys.rotp_7su.capability;

import com.thedoggys.rotp_7su.network.AddonPackets;
import com.thedoggys.rotp_7su.network.packets.server.MiraclesAura2Packet;
import com.thedoggys.rotp_7su.network.packets.server.MiraclesAuraPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class LivingData implements INBTSerializable<CompoundNBT> {
    private final LivingEntity entity;
    private boolean gentlyWeeps = false;
    private boolean wipePresence = false;

    public LivingData(LivingEntity entity) {
        this.entity = entity;
    }

    public void syncWithAnyPlayer(ServerPlayerEntity player) {
    }


    public void syncWithEntityOnly(ServerPlayerEntity player) {
    }
    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.gentlyWeeps = nbt.getBoolean("GentlyWeeps");
        this.wipePresence = nbt.getBoolean("WipePresence");
    }
    public void setGentlyWeeps(boolean gentlyWeeps) {
        this.gentlyWeeps = gentlyWeeps;
        if(entity instanceof ServerPlayerEntity){
            AddonPackets.sendToClient(new MiraclesAuraPacket(entity.getId(),gentlyWeeps),(ServerPlayerEntity) entity);
        }
    }

    public boolean isGentlyWeeps() {
        return this.gentlyWeeps;
    }

    public void setWipePresence(boolean wipePresence) {
        this.wipePresence = wipePresence;
        if(entity instanceof ServerPlayerEntity){
            AddonPackets.sendToClient(new MiraclesAura2Packet(entity.getId(),wipePresence),(ServerPlayerEntity) entity);
        }
    }

    public boolean isWipePresence() {
        return this.wipePresence;
    }
}
