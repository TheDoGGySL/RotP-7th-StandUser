package com.thedoggys.rotp_7su.client.render.model;

import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.thedoggys.rotp_7su.entity.JoykillerEntity;
import net.minecraft.client.renderer.model.ModelRenderer;

public class JoykillerModel extends HumanoidStandModel<JoykillerEntity> {
    private ModelRenderer axe;

    public JoykillerModel() {
        super();
    }

    @Override
    public void prepareMobModel(JoykillerEntity entity, float walkAnimPos, float walkAnimSpeed, float partialTick) {
        super.prepareMobModel(entity, walkAnimPos, walkAnimSpeed, partialTick);
        if (axe != null) {
            axe.visible = entity.hasAxe();
        }
    }
}
