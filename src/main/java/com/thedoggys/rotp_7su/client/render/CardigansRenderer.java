package com.thedoggys.rotp_7su.client.render;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.entity.CardigansEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class CardigansRenderer extends StandEntityRenderer<CardigansEntity, StandEntityModel<CardigansEntity>> {
    
    public CardigansRenderer(EntityRendererManager renderManager) {
        super(renderManager, 
                StandModelRegistry.registerModel(new ResourceLocation(AddonMain.MOD_ID, "cardigans"), CardigansModel::new),
                new ResourceLocation(AddonMain.MOD_ID, "textures/entity/stand/cardigans.png"), 0);
    }
}
