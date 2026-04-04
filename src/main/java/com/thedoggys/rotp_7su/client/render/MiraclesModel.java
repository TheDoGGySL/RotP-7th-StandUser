package com.thedoggys.rotp_7su.client.render;

import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.thedoggys.rotp_7su.entity.MiraclesEntity;

public class MiraclesModel extends HumanoidStandModel<MiraclesEntity> {

    public MiraclesModel() {
        super();
    }

    @Override
    public void prepareMobModel(MiraclesEntity entity, float walkAnimPos, float walkAnimSpeed, float partialTick) {
        super.prepareMobModel(entity, walkAnimPos, walkAnimSpeed, partialTick);
    }
}
