package com.thedoggys.rotp_7su.client.render;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import com.github.standobyte.jojo.util.mc.entitysubtype.EntitySubtype;
import com.github.standobyte.jojo.util.mc.entitysubtype.EntityTypeToInstance;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.entity.MiraclesEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;

public class MiraclesRenderer extends StandEntityRenderer<MiraclesEntity, StandEntityModel<MiraclesEntity>> {

    public MiraclesRenderer(EntityRendererManager renderManager) {
        super(renderManager,
                StandModelRegistry.registerModel(new ResourceLocation(AddonMain.MOD_ID, "miracles"), MiraclesModel::new),
                new ResourceLocation(AddonMain.MOD_ID, "textures/entity/stand/miracles.png"), 0);
    }
    public static int getOverlayCoordsForEntity(LivingEntity entity, float p_229117_1_) {
        return OverlayTexture.pack(OverlayTexture.u(p_229117_1_), OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
    }
    @Override
    public void render(MiraclesEntity whitesnake, float yRotation, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight){
        final Minecraft mc = Minecraft.getInstance();
        float ticks = whitesnake.tickCount + partialTick;
        float tf_progress = MathHelper.clamp(whitesnake.shapeshiftTickDifference(ticks)/20f, 0, 1);

        if (whitesnake.getEntityForDisguise().isPresent()){
            //testing
            EntityType<? extends LivingEntity> entityType = (EntityType<? extends LivingEntity>) whitesnake.getEntityForDisguise().get();
            LivingRenderer<LivingEntity, EntityModel<LivingEntity>> renderer = (LivingRenderer<LivingEntity, EntityModel<LivingEntity>>) mc.getEntityRenderDispatcher().renderers.get(entityType);
            //BEWARE
            final LivingEntity living = (LivingEntity) EntityTypeToInstance.getEntityInstance(EntitySubtype.base(entityType), mc.level);
            final EntityModel<LivingEntity> model = renderer.getModel();
            final ResourceLocation texture = renderer.getTextureLocation(living);

            float yHeadRotation = MathHelper.rotLerp(partialTick, whitesnake.yHeadRotO, whitesnake.yHeadRot);
            float yBodyRotation = MathHelper.rotLerp(partialTick, whitesnake.yBodyRotO, whitesnake.yBodyRot);
            float f2 = yHeadRotation - yBodyRotation;

            float xRotation = MathHelper.lerp(partialTick, whitesnake.xRotO, whitesnake.xRot);
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - yBodyRotation));

            float animSpeed = 0;
            float animPos = 0;
            float attackTime = 0;

            matrixStack.pushPose();
            matrixStack.scale(-1, -1, 1);
            matrixStack.scale(tf_progress, tf_progress, tf_progress);
            matrixStack.translate(0d, -1.501d, 0d);
            model.young=living.isBaby();
            model.riding=false;
            model.attackTime=attackTime;
            //animations
            model.prepareMobModel(living, animSpeed, animPos, partialTick);
            model.setupAnim(living, animSpeed, animPos, ticks, f2, xRotation);
            //texture
            if (texture!=null){
                final RenderType rendertype = model.renderType(texture);
                if (rendertype!=null){
                    final IVertexBuilder ivertexbuilder = buffer.getBuffer(rendertype);
                    model.renderToBuffer(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, this.calcAlpha(whitesnake, partialTick));
                }
            }
            //finishing point
            matrixStack.popPose();
            MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>>(whitesnake, renderer, partialTick, matrixStack, buffer, packedLight));
        }else{
            super.render(whitesnake, yRotation, partialTick, matrixStack, buffer, packedLight);
        }
    }
}
