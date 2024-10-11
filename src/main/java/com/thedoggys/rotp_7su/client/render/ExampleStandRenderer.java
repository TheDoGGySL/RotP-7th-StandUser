package com.thedoggys.rotp_7su.client.render;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.entity.ExampleStandEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExampleStandRenderer extends StandEntityRenderer<ExampleStandEntity, StandEntityModel<ExampleStandEntity>> {
    
    public ExampleStandRenderer(EntityRendererManager renderManager) {
        super(renderManager, 
                StandModelRegistry.registerModel(new ResourceLocation(AddonMain.MOD_ID, "example_stand"), ExampleStandModel::new), 
                new ResourceLocation(AddonMain.MOD_ID, "textures/entity/stand/example_stand.png"), 0);
    }
}
