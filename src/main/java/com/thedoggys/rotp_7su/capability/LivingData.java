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
    private int unitType = 0;
    private boolean gentlyWeeps = false;
    private boolean wipePresence = false;
    private boolean landedMines = false;
    private boolean soldierStay = false;
    private boolean tankStay = false;
    private boolean copterStay = false;
    private boolean explosiveMissiles = true;
    private boolean shouldChangeFormation = false;
    private boolean summonSoldier = true;
    private boolean summonTank = true;
    private boolean summonCopter = true;


    private int formation = 0;
    private int prevFormation = 0;
    public LivingData(LivingEntity entity) {
        this.entity = entity;
    }

    public void setFormation(int formation){
        this.prevFormation = this.formation;
        this.formation = formation;
    }
    public int getFormation(){
        return this.formation;
    }
    public int getPrevFormation(){
        return this.prevFormation;
    }
    public boolean shouldChangeFormation() {
        return shouldChangeFormation;
    }
    public void setShouldChangeFormation(boolean shouldChangeFormation) {
        this.shouldChangeFormation = shouldChangeFormation;
    }

    public void syncWithAnyPlayer(ServerPlayerEntity player) {
    }


    public void syncWithEntityOnly(ServerPlayerEntity player) {
    }
    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("SoldierStay",this.soldierStay);
        nbt.putBoolean("TankStay", this.tankStay);
        nbt.putBoolean("CopterStay", this.copterStay);
        nbt.putInt("Unit type", this.unitType);
        nbt.putBoolean("Mines",this.landedMines);
        nbt.putBoolean("Missile",this.explosiveMissiles);
        nbt.putBoolean("summonSoldier",this.summonSoldier);
        nbt.putBoolean("summonTank",this.summonTank);
        nbt.putBoolean("summonCopter",this.summonCopter);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.soldierStay = nbt.getBoolean("SoldierStay");
        this.tankStay = nbt.getBoolean("TankStay");
        this.copterStay = nbt.getBoolean("CopterStay");
        this.unitType = nbt.getInt("Unit type");
        this.landedMines = nbt.getBoolean("Mines");
        this.explosiveMissiles = nbt.getBoolean("Missile");
        this.summonSoldier = nbt.getBoolean("summonSoldier");
        this.summonTank = nbt.getBoolean("summonTank");
        this.summonCopter = nbt.getBoolean("summonCopter");
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
