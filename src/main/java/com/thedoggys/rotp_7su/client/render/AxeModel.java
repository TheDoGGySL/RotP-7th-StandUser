package com.thedoggys.rotp_7su.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.thedoggys.rotp_7su.entity.AxeEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class AxeModel extends EntityModel<AxeEntity> {
    private final ModelRenderer axe;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;;

    public AxeModel() {
        texWidth = 32;
        texHeight = 32;

        texWidth = 32;
        texHeight = 32;

        axe = new ModelRenderer(this);
        axe.setPos(0.0F, -3.5349F, 1.6892F);
        setRotationAngle(axe, 0.829F, 0.0F, 0.0F);
        axe.texOffs(0, 7).addBox(-1.0F, -6.4908F, -1.2977F, 1.0F, 8.0F, 2.0F, 0.0F, false);
        axe.texOffs(0, 0).addBox(-1.5F, -7.4908F, -2.2977F, 2.0F, 4.0F, 3.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(0.0F, 2.9743F, 0.3916F);
        axe.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.3927F, 0.0F, 0.0F);
        cube_r1.texOffs(12, 6).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(0.0F, -3.4907F, -2.7977F);
        axe.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.1309F, 0.0F, 0.0F);
        cube_r2.texOffs(6, 7).addBox(-1.0F, -4.5F, -1.0F, 1.0F, 6.0F, 2.0F, 0.0F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(0.0F, -3.3691F, 0.8129F);
        axe.addChild(cube_r3);
        setRotationAngle(cube_r3, -0.2182F, 0.0F, 0.0F);
        cube_r3.texOffs(10, 0).addBox(-1.0F, -4.0F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, false);
    }


    @Override
    public void setupAnim(AxeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!(entity.getDeltaMovement().x <= 0.01f && entity.getDeltaMovement().y <= 0.01f && entity.getDeltaMovement().z <= 0.01f)) {
            axe.yRot = netHeadYaw * ((float) Math.PI / 180F);
            axe.xRot = ageInTicks * 2F;
        }
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        axe.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
