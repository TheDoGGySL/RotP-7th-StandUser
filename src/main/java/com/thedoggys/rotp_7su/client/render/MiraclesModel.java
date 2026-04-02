package com.thedoggys.rotp_7su.client.render;

import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.github.standobyte.jojo.client.render.entity.pose.RotationAngle;
import com.thedoggys.rotp_7su.entity.MiraclesEntity;
import com.thedoggys.rotp_7su.init.InitStands;
import net.minecraft.client.renderer.model.ModelRenderer;

import javax.annotation.Nullable;

public class MiraclesModel extends HumanoidStandModel<MiraclesEntity> {

    public MiraclesModel() {
        super();
    }

    @Override
    public void prepareMobModel(MiraclesEntity entity, float walkAnimPos, float walkAnimSpeed, float partialTick) {
        super.prepareMobModel(entity, walkAnimPos, walkAnimSpeed, partialTick);
    }
}
