package com.thedoggys.rotp_7su.client.render;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.entity.JoykillerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class JoykillerRenderer extends StandEntityRenderer<JoykillerEntity, StandEntityModel<JoykillerEntity>> {

    public JoykillerRenderer(EntityRendererManager renderManager) {
        super(renderManager,
                StandModelRegistry.registerModel(new ResourceLocation(AddonMain.MOD_ID, "the_joykiller"), JoykillerModel::new),
                new ResourceLocation(AddonMain.MOD_ID, "textures/entity/stand/the_joykiller.png"), 0);
    }
}
