package com.thedoggys.rotp_7su.client.render.renderer;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.capability.LivingDataProvider;
import com.thedoggys.rotp_7su.client.render.model.MiraclesModel;
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
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class MiraclesRenderer extends StandEntityRenderer<MiraclesEntity, StandEntityModel<MiraclesEntity>> {

    // i promise not to do this shit again, excuse me god
    // TODO: full rewrite of disguise
    private static final List<String> DISGUISE_STANDS = Arrays.asList(
            "jojo:star_platinum",
            "jojo:the_world",
            "jojo:crazy_diamond",
            "jojo:gold_experience",
            "jojo:hierophant_green",
            "jojo:magicians_red",
            "rotp_7su:cardigans"
    );

    public MiraclesRenderer(EntityRendererManager renderManager) {
        super(renderManager,
                StandModelRegistry.registerModel(new ResourceLocation(AddonMain.MOD_ID, "miracles"), MiraclesModel::new),
                new ResourceLocation(AddonMain.MOD_ID, "textures/entity/stand/miracles.png"), 0);
    }

//    public static int getOverlayCoordsForEntity(LivingEntity entity, float p_229117_1_) {
//        return OverlayTexture.pack(OverlayTexture.u(p_229117_1_), OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
//    }

    @Override
    public void render(MiraclesEntity whitesnake, float yRotation, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        final Minecraft mc = Minecraft.getInstance();
        float ticks = whitesnake.tickCount + partialTick;
        float tf_progress = MathHelper.clamp(whitesnake.shapeshiftTickDifference(ticks) / 20f, 0, 1);

        LivingEntity user = whitesnake.getUser();
        boolean shouldDisguise = false;
        if (user != null) {
            shouldDisguise = user.getCapability(LivingDataProvider.CAPABILITY)
                    .map(data -> data.isWipePresence())
                    .orElse(false);
        }

        if (shouldDisguise) {
            Random rand = new Random(whitesnake.getId());
            String standId = DISGUISE_STANDS.get(rand.nextInt(DISGUISE_STANDS.size()));
            EntityType<?> type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(standId));
            if (type == null) return;

            LivingRenderer<LivingEntity, EntityModel<LivingEntity>> renderer =
                    (LivingRenderer<LivingEntity, EntityModel<LivingEntity>>) mc.getEntityRenderDispatcher().renderers.get(type);
            if (renderer == null) return;

            LivingEntity dummyEntity = (LivingEntity) type.create(mc.level);
            if (dummyEntity == null) return;
            if (!(dummyEntity instanceof StandEntity)) return;

            EntityModel<LivingEntity> model = renderer.getModel();
            ResourceLocation texture = renderer.getTextureLocation(dummyEntity);

            float yHeadRotation = MathHelper.rotLerp(partialTick, whitesnake.yHeadRotO, whitesnake.yHeadRot);
            float yBodyRotation = MathHelper.rotLerp(partialTick, whitesnake.yBodyRotO, whitesnake.yBodyRot);
            float f2 = yHeadRotation - yBodyRotation;
            float xRotation = MathHelper.lerp(partialTick, whitesnake.xRotO, whitesnake.xRot);
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - yBodyRotation));

            float limbSwing = whitesnake.walkDistO + (whitesnake.walkDist - whitesnake.walkDistO) * partialTick;
            float limbSwingAmount = whitesnake.walkDist - whitesnake.walkDistO;
            float attackTime = 0;

            matrixStack.pushPose();
            matrixStack.scale(-1, -1, 1);
            matrixStack.scale(tf_progress, tf_progress, tf_progress);
            matrixStack.translate(0, -1, 0);

            model.young = dummyEntity.isBaby();
            model.riding = false;
            model.attackTime = attackTime;

            model.prepareMobModel(dummyEntity, limbSwing, limbSwingAmount, partialTick);
            model.setupAnim(dummyEntity, limbSwing, limbSwingAmount, ticks, f2, xRotation);

            if (texture != null) {
                RenderType rendertype = model.renderType(texture);
                if (rendertype != null) {
                    IVertexBuilder ivertexbuilder = buffer.getBuffer(rendertype);
                    model.renderToBuffer(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, this.calcAlpha(whitesnake, partialTick));
                }
            }

            matrixStack.popPose();
            MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Post<>(whitesnake, renderer, partialTick, matrixStack, buffer, packedLight));
        } else {
            super.render(whitesnake, yRotation, partialTick, matrixStack, buffer, packedLight);
        }
    }
}