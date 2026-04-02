package com.thedoggys.rotp_7su.client.ui.screen.specials;

import com.github.standobyte.jojo.client.standskin.StandSkinsManager;
import com.github.standobyte.jojo.client.ui.BlitFloat;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.thedoggys.rotp_7su.AddonMain;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.concurrent.atomic.AtomicReference;

public class FormationIcon {
    private static ResourceLocation FIRST = new ResourceLocation(AddonMain.MOD_ID, "textures/action/first.png");
    private static ResourceLocation SECOND = new ResourceLocation(AddonMain.MOD_ID, "textures/action/second.png");
    private static ResourceLocation THIRD = new ResourceLocation(AddonMain.MOD_ID, "textures/action/third.png");
    private static ResourceLocation FOURTH = new ResourceLocation(AddonMain.MOD_ID, "textures/action/fourth.png");
    private static ResourceLocation FIFTH = new ResourceLocation(AddonMain.MOD_ID, "textures/action/fifth.png");
    private static ResourceLocation SIXTH = new ResourceLocation(AddonMain.MOD_ID, "textures/action/sixth.png");

    public static ResourceLocation getIconByType(FormationType type){
        switch (type){
            case SECOND:
                return SECOND;
            case THIRD:
                return THIRD;
            case FOURTH:
                return FOURTH;
            case FIFTH:
                return FIFTH;
            case SIXTH:
                return SIXTH;
            default:
                return FIRST;
        }
    }

    public static void renderIcon(FormationType type, MatrixStack matrixStack, float x, float y){
        AtomicReference<ResourceLocation> icon = new AtomicReference<>(getIconByType(type));
        IStandPower.getStandPowerOptional(Minecraft.getInstance().player).ifPresent(power -> {
            icon.set(StandSkinsManager.getInstance().getRemappedResPath(manager -> manager
                    .getStandSkin(power.getStandInstance().get()), icon.get()));
        });
        Minecraft.getInstance().getTextureManager().bind(icon.get());
        BlitFloat.blitFloat(matrixStack, x, y, 0, 0, 16, 16, 16, 16);
    }
}
