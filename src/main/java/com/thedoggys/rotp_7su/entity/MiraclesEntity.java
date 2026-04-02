package com.thedoggys.rotp_7su.entity;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

import java.util.Optional;

public class MiraclesEntity extends StandEntity {
    public MiraclesEntity(StandEntityType<MiraclesEntity> type, World world) {
        super(type, world);
        unsummonOffset = getDefaultOffsetFromUser().copy();
    }

    private StandRelativeOffset offsetDefault = StandRelativeOffset.withYOffset(1, 0.75, -1);
    private StandRelativeOffset offsetDefaultArmsOnly = StandRelativeOffset.withYOffset(0, 0, 0.15);

    public StandRelativeOffset getDefaultOffsetFromUser() {
        return isArmsOnlyMode() ? offsetDefaultArmsOnly : offsetDefault;
    }

    private float LAST_SHAPESHIFT = 0f;
    private boolean DISGUISED = false;
    private Optional<EntityType<?>> DISGUISE_ENTITY = Optional.empty();

    //methods
    public Optional<EntityType<?>> getEntityForDisguise() {
        return this.DISGUISE_ENTITY;
    }

    public boolean isDisguisedOnce() {
        return this.DISGUISED;
    }

    public void setDisguisedOnce(boolean value) {
        this.DISGUISED = value;
    }

    public float getShapeshiftTick() {
        return this.LAST_SHAPESHIFT;
    }

    public void setShapeshiftTick(float tick) {
        this.LAST_SHAPESHIFT = tick;
    }

    public float shapeshiftTickDifference(float now) {
        return now - this.getShapeshiftTick();
    }

    public void setEntityForDisguise(EntityType<?> entity) {
        this.DISGUISE_ENTITY = Optional.ofNullable(entity);
    }
}
