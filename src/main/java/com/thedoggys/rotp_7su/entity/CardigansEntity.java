package com.thedoggys.rotp_7su.entity;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;

import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class CardigansEntity extends StandEntity {

    public CardigansEntity(StandEntityType<CardigansEntity> type, World world) {
        super(type, world);
    }
    private final StandRelativeOffset offsetDefault = StandRelativeOffset.withYOffset(0, .5, -.75);

    public StandRelativeOffset getDefaultOffsetFromUser() {return offsetDefault;}
}
