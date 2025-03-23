package com.thedoggys.rotp_7su.client.render;

import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.github.standobyte.jojo.client.render.entity.pose.*;
import com.thedoggys.rotp_7su.entity.CardigansEntity;

import com.thedoggys.rotp_7su.init.InitStands;
import net.minecraft.client.renderer.model.ModelRenderer;

import javax.annotation.Nullable;

public class CardigansModel extends HumanoidStandModel<CardigansEntity> {
    private final ModelRenderer scalpel;
    private final ModelRenderer springe;
    private final ModelRenderer def2;
    private final ModelRenderer def1;


	public CardigansModel() {
        super();
        addHumanoidBaseBoxes(null);
        texWidth = 128;
        texHeight = 128;

        springe = new ModelRenderer(this);
        springe.setPos(0.0F, 5.0F, -3.0F);
        rightForeArm.addChild(springe);
        springe.texOffs(67, 70).addBox(0.0F, -1.0F, -6.0F, 0.0F, 3.0F, 8.0F, 0.0F, false);

        scalpel = new ModelRenderer(this);
        scalpel.setPos(6.0F, 18.0F, 0.0F);
        rightForeArm.addChild(scalpel);
        scalpel.texOffs(73, 12).addBox(-6.0F, -14.0F, -9.0F, 0.0F, 3.0F, 10.0F, 0.0F, false);

        def1 = new ModelRenderer(this);
        def1.setPos(-6.0F, 18.0F, 0.0F);
        leftForeArm.addChild(def1);
        def1.texOffs(8, 90).addBox(4.5F, -13.0F, -3.0F, 3.0F, 2.0F, 6.0F, 0.0F, false);


        def2 = new ModelRenderer(this);
        def2.setPos(6.0F, 18.0F, 0.0F);
        rightForeArm.addChild(def2);
        def2.texOffs(28, 90).addBox(-7.5F, -13.0F, -3.0F, 3.0F, 2.0F, 6.0F, 0.0F, false);
    }


	@Override // TODO summon poses
    protected RotationAngle[][] initSummonPoseRotations() {
        return new RotationAngle[][] {
            new RotationAngle[] {
                    
            },
            new RotationAngle[] {
                    
            }
		};
    }
//    @Override
//    protected void initActionPoses() {
//        ModelPose.ModelAnim<CardigansEntity> armsRotation = (rotationAmount, entity, ticks, yRotOffsetRad, xRotRad) -> {
//            setSecondXRot(rightArm, xRotRad);
//        };
//        RotationAngle[] barrageRightStart = new RotationAngle[] {
//                RotationAngle.fromDegrees(body, 0, 0, 0),
//                RotationAngle.fromDegrees(upperPart, 0, -45, 0),
//                RotationAngle.fromDegrees(leftArm, 45, 0, -60),
//                RotationAngle.fromDegrees(leftForeArm, 0, 0, 0),
//                RotationAngle.fromDegrees(rightArm, 89, 0, 90),
//                RotationAngle.fromDegrees(rightForeArm, -135, 0, 0),
//        };
//
//        RotationAngle[] barrageRightImpact = new RotationAngle[] {
//                RotationAngle.fromDegrees(body, 0, 0, 0),
//                RotationAngle.fromDegrees(upperPart, 0, -45, 0),
//                RotationAngle.fromDegrees(leftArm, 45, 0, -60),
//                RotationAngle.fromDegrees(leftForeArm, 0, 0, 0),
//                RotationAngle.fromDegrees(rightArm, -90, 45, 0),
//                RotationAngle.fromDegrees(rightForeArm, 0, 0, 0),
//        };
//
//        ModelPose<CardigansEntity> sweepPose1 = new ModelPose<>(new RotationAngle[]{
//                RotationAngle.fromDegrees(head, 0F, -45F, 0F),
//                RotationAngle.fromDegrees(body, 0F, -90F, 0F),
//                RotationAngle.fromDegrees(upperPart, 0F, -30F, 0F),
//                RotationAngle.fromDegrees(rightArm, 0F, 45F, 75F),
//                RotationAngle.fromDegrees(rightForeArm, 0F, 0F, -120F),
//        });
//        ModelPose<CardigansEntity> sweepPose2 = new ModelPose<>(new RotationAngle[]{
//                RotationAngle.fromDegrees(head, 0F, -36F, 0F),
//                RotationAngle.fromDegrees(body, 0F, -69F, 0F),
//                RotationAngle.fromDegrees(upperPart, 0F, -24F, 0F),
//                RotationAngle.fromDegrees(rightArm, 2.5F, 36F, 84F),
//                RotationAngle.fromDegrees(rightForeArm, 0F, 0F, 0F),
//        });
//        ModelPose<CardigansEntity> sweepPose3 = new ModelPose<>(new RotationAngle[]{
//                RotationAngle.fromDegrees(head, 0F, 0F, 0F),
//                RotationAngle.fromDegrees(body, 0F, 15F, 0F),
//                RotationAngle.fromDegrees(upperPart, 0F, 0F, 0F),
//                RotationAngle.fromDegrees(leftArm, 45F, -30F, -75F),
//                RotationAngle.fromDegrees(leftForeArm, 0F, 0F, 30F),
//                RotationAngle.fromDegrees(rightArm, 12.5F, 0F, 120F)
//        });
//        ModelPose<CardigansEntity> springe_coll1 = new ModelPose<>(new RotationAngle[]{
//                RotationAngle.fromDegrees(leftArm, -45, 0, 0),
//                RotationAngle.fromDegrees(leftForeArm, -40, 0, 0),
//        });
//        ModelPose<CardigansEntity> springe_coll2 = new ModelPose<>(new RotationAngle[]{
//                RotationAngle.fromDegrees(springe, 90, 0, 0)
//        });
//        ModelPose<CardigansEntity> springe_coll3 = new ModelPose<>(new RotationAngle[]{
//                RotationAngle.fromDegrees(leftArm, 90, 0, 0),
//                RotationAngle.fromDegrees(leftForeArm, 0, 0, 0),
//        });
//        IModelPose<CardigansEntity> barrageStabStart = new ModelPoseSided<>(
//                new ModelPose<CardigansEntity>(mirrorAngles(barrageRightStart)).setAdditionalAnim(armsRotation),
//                new ModelPose<CardigansEntity>(barrageRightStart).setAdditionalAnim(armsRotation));
//
//        IModelPose<CardigansEntity> barrageStabImpact = new ModelPoseSided<>(
//                new ModelPose<CardigansEntity>(mirrorAngles(barrageRightImpact)).setAdditionalAnim(armsRotation),
//                new ModelPose<CardigansEntity>(barrageRightImpact).setAdditionalAnim(armsRotation));
//
//        actionAnim.put(StandPose.HEAVY_ATTACK, new PosedActionAnimation.Builder<CardigansEntity>()
//                .addPose(StandEntityAction.Phase.WINDUP, new ModelPoseTransition<>(idlePose, sweepPose1))
//                .addPose(StandEntityAction.Phase.PERFORM, new ModelPoseTransitionMultiple.Builder<>(sweepPose1)
//                        .addPose(0.2F, sweepPose2)
//                        .build(sweepPose3))
//                .build(idlePose));
//        actionAnim.put(StandPose.LIGHT_ATTACK, new PosedActionAnimation.Builder<CardigansEntity>()
//                .addPose(StandEntityAction.Phase.WINDUP, new ModelPoseTransition<CardigansEntity>(barrageStabStart, barrageStabImpact)
//                        .setEasing(sw -> sw < 0.75F ?
//                                16 / 9  * sw * sw    - 8 / 3 * sw    + 1
//                                : 16    * sw * sw    - 24    * sw    + 9))
//                .addPose(StandEntityAction.Phase.PERFORM, new RigidModelPose<>(barrageStabImpact))
//                .addPose(StandEntityAction.Phase.RECOVERY, new ModelPoseTransition<>(barrageStabImpact, idlePose)
//                        .setEasing(pr -> Math.max(4F * (pr - 1) + 1, 0F)))
//                .build(idlePose));
//        actionAnim.put(INJECT_TARGET, new PosedActionAnimation.Builder<CardigansEntity>()
//                .addPose(StandEntityAction.Phase.BUTTON_HOLD, new ModelPoseTransitionMultiple.Builder<>(idlePose)
//                        .addPose(0.1F, springe_coll1)
//                        .addPose(0.75F,springe_coll2)
//                        .build(springe_coll3))
//                .addPose(StandEntityAction.Phase.PERFORM, new ModelPoseTransitionMultiple.Builder<>(springe_coll2)
//                        .addPose(0.5F, springe_coll3)
//                        .build(springe_coll3))
//                .addPose(StandEntityAction.Phase.RECOVERY, new ModelPoseTransitionMultiple.Builder<>(springe_coll3)
//                        .addPose(0.5F, springe_coll3)
//                        .build(idlePose))
//                .build(idlePose));;
//    }

    @Override
    public void prepareMobModel(@Nullable CardigansEntity entity, float walkAnimPos, float walkAnimSpeed, float partialTick) {
        super.prepareMobModel(entity, walkAnimPos, walkAnimSpeed, partialTick);
        if (entity != null) {
            this.def1.visible = entity.getCurrentTaskAction() == InitStands.CARDIGANS_SHOCK.get();
            this.def2.visible = entity.getCurrentTaskAction() == InitStands.CARDIGANS_SHOCK.get();
            this.springe.visible = entity.getCurrentTaskAction() == InitStands.CARDIGANS_HEAL.get() || entity.getCurrentTaskAction() == InitStands.CARDIGANS_HEAL_YOURSELF.get() || entity.getCurrentTaskAction() == InitStands.CARDIGANS_ANASTESIA.get() || entity.getCurrentTaskAction() == InitStands.CARDIGANS_ANASTHESIA_YOURSELF.get() || entity.getCurrentTaskAction() == InitStands.CARDIGANS_DOPPING.get();
            this.scalpel.visible = entity.getCurrentTaskAction() == InitStands.CARDIGANS_SLASH.get() || entity.getCurrentTaskAction() == InitStands.CARDIGANS_SLASH_LIGHT.get() || entity.getCurrentTaskAction() == InitStands.CARDIGANS_SLASH_HEAVY.get();
        }
    }



//    @Override // TODO idle pose
//    protected ModelPose<CardigansEntity> initIdlePose() {
//        return new ModelPose<>(new RotationAngle[]{
//                RotationAngle.fromDegrees(leftArm, 0F, 0F, -5F),
//                RotationAngle.fromDegrees(leftForeArm, 0F, 0F, 2.5F),
//                RotationAngle.fromDegrees(rightArm, 0F, 0F, 5F),
//                RotationAngle.fromDegrees(rightForeArm, 0F, 0F, -2.5F),
//                RotationAngle.fromDegrees(upperPart, 0F, 0F, 0F),
//        });
//    }
//
//    @Override
//    protected ModelPose<CardigansEntity> initIdlePose2Loop() {
//        return new ModelPose<>(new RotationAngle[]{
//                RotationAngle.fromDegrees(leftArm, 0F, 0F, -10F),
//                RotationAngle.fromDegrees(leftForeArm, 0F, 0F, 7.5F),
//                RotationAngle.fromDegrees(rightArm, 0F, 0F, 10F),
//                RotationAngle.fromDegrees(rightForeArm, 0F, 0F, -7.5F),
//                RotationAngle.fromDegrees(upperPart, 0F, 0F, 0F),
//        });
//    }
}