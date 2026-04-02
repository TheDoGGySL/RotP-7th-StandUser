package com.thedoggys.rotp_7su.entity;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class JoykillerEntity extends StandEntity {
    private static final DataParameter<Boolean> HAS_AXE =
            EntityDataManager.defineId(JoykillerEntity.class, DataSerializers.BOOLEAN);

    public JoykillerEntity(StandEntityType<JoykillerEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(HAS_AXE, true);
    }

    public boolean hasAxe() {
        return entityData.get(HAS_AXE);
    }

    public void setHasAxe(boolean hasPickaxe) {
        entityData.set(HAS_AXE, hasPickaxe);
    }
}
