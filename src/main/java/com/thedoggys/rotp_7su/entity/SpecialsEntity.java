package com.thedoggys.rotp_7su.entity;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;

import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class SpecialsEntity extends StandEntity implements IEntityAdditionalSpawnData {
    private int index = 0;

    public SpecialsEntity(StandEntityType<? extends StandEntity> type, World world) {
        super(type, world);
    }
    
    public void setSpecialsIndex(int index) {
        this.index = index;
    }
    
    public int getSpecialsIndex() {
        return index;
    }
    
    @Override
    public boolean canAttack(LivingEntity entity) {
        if (entity instanceof StandEntity) {
            LivingEntity user = this.getUser();
            if (user != null && ((StandEntity) entity).getUser() == user) {
                return false;
            }
        }
        return super.canAttack(entity);
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        super.writeSpawnData(buffer);
        buffer.writeVarInt(index);
        buffer.writeDouble(getDefaultOffsetFromUser().getLeft());
        buffer.writeDouble(getDefaultOffsetFromUser().getForward());
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        super.readSpawnData(additionalData);
        this.index = additionalData.readVarInt();
        double left = additionalData.readDouble();
        double forward = additionalData.readDouble();
        setDefaultOffsetFromUser(StandRelativeOffset.withYOffset(left, 0.2, forward));
    }

}
