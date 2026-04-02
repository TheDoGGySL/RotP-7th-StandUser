package com.thedoggys.rotp_7su.client.render;

import com.github.standobyte.jojo.client.render.entity.renderer.SimpleEntityRenderer;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.entity.AxeEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class AxeRenderer extends SimpleEntityRenderer<AxeEntity, AxeModel> {

    public AxeRenderer(EntityRendererManager renderManager) {
        super(renderManager, new AxeModel(),
                new ResourceLocation(AddonMain.MOD_ID, "textures/entity/axe.png"));
    }
}
