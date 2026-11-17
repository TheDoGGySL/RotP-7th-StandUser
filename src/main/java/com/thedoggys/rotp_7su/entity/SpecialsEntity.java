package com.thedoggys.rotp_7su.entity;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;

import com.thedoggys.rotp_7su.specials.SpecialsEntities;
import com.thedoggys.rotp_7su.specials.SpecialsStandType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class SpecialsEntity extends StandEntity implements IEntityAdditionalSpawnData {
    private int index = 0;
    private StandRelativeOffset formationOffset = null;
    private long lastClientTickTime = -1;

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
    public void setDefaultOffsetFromUser(StandRelativeOffset offset) {
        super.setDefaultOffsetFromUser(offset);
        this.formationOffset = offset;
    }

    public StandRelativeOffset getFormationOffset() {
        return formationOffset != null ? formationOffset : getDefaultOffsetFromUser();
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

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide()) {
            lastClientTickTime = level.getGameTime();
        }
        SpecialsEntities summonedEntities = null;
        if (getUserPower() != null) {
            summonedEntities = SpecialsStandType.getSpecialsEntities(getUserPower()).orElse(null);
            if (summonedEntities != null) {
                for (StandEntity stand : summonedEntities.specialsEntities) {
                    if (stand.isFollowingUser()) {
                        stand.updatePosition();
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        super.writeSpawnData(buffer);
        buffer.writeVarInt(index);
        buffer.writeDouble(getFormationOffset().getLeft());
        buffer.writeDouble(getFormationOffset().getForward());
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        super.readSpawnData(additionalData);
        this.index = additionalData.readVarInt();
        double left = additionalData.readDouble();
        double forward = additionalData.readDouble();
        setDefaultOffsetFromUser(StandRelativeOffset.withYOffset(left, 0.2, forward));
    }

    public static void updatePositionAfterUserTick(LivingEntity user) {
        long gameTime = user.level.getGameTime();
        for (SpecialsEntity stand : user.level.getEntitiesOfClass(SpecialsEntity.class, user.getBoundingBox().inflate(16), 
                entity -> entity.getUser() == user)) {
            if (stand.lastClientTickTime == gameTime && stand.isFollowingUser()) {
                if (!stand.getCurrentTask().isPresent()) {
                    stand.defaultRotation();
                }
                stand.updatePosition();
            }
        }
    }
}
