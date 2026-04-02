package com.thedoggys.rotp_7su.client.render;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.entity.JoykillerEntity;
import com.thedoggys.rotp_7su.entity.RedGarlandEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class RedGarlandRenderer extends StandEntityRenderer<RedGarlandEntity, StandEntityModel<RedGarlandEntity>> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[7];
    private static final ResourceLocation[] TEXTURES_SKIN2 = new ResourceLocation[7];

    static {
        for (int i = 0; i < 7; i++) {
            TEXTURES[i] = new ResourceLocation(
                    AddonMain.MOD_ID,
                    "textures/entity/stand/red_garland" + (i + 1) + ".png"
            );
            TEXTURES_SKIN2[i] = new ResourceLocation(
                    AddonMain.MOD_ID,
                    "textures/entity/stand/red_garland_" + (i + 1) + ".png"
            );
        }
    }

    public RedGarlandRenderer(EntityRendererManager renderManager) {
        super(renderManager,
                StandModelRegistry.registerModel(
                        new ResourceLocation(AddonMain.MOD_ID, "red_garland"),
                        RedGarlandModel::new
                ),
                TEXTURES[0],
                0
        );
    }

    @Override
    public ResourceLocation getTextureLocation(RedGarlandEntity entity) {
        if (entity != null) {
            int frame = (entity.tickCount / 5) % 7;
            if (entity.getStandSkin().equals("rotp_7su:skinc")) {
                return TEXTURES_SKIN2[frame];
            }
            return TEXTURES[frame];
        }
        return TEXTURES[0];
    }
    //todo:fix skin
}
