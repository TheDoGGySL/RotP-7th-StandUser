package com.thedoggys.rotp_7su.client.render;

import java.util.function.Supplier;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import com.thedoggys.rotp_7su.entity.SpecialsEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class SpecialsRenderer extends StandEntityRenderer<SpecialsEntity, StandEntityModel<SpecialsEntity>> {
    
    public SpecialsRenderer(EntityRendererManager renderManager, ResourceLocation modelId, 
            Supplier<? extends StandEntityModel<SpecialsEntity>> modelConstructor, ResourceLocation texture) {
        super(renderManager, 
                StandModelRegistry.registerModel(modelId, modelConstructor), 
                texture, 0);
    }
    
}
