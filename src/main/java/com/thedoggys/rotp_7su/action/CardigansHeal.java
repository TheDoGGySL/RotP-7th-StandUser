package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class CardigansHeal extends StandEntityLightAttack {
    boolean manifest = true;


    public CardigansHeal(StandEntityLightAttack.Builder builder) {
        super(builder);
    }


    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide) {

            LivingEntity user = userPower.getUser();

            if (task.getTarget().getType() == ActionTarget.TargetType.ENTITY) {
                Entity target = task.getTarget().getEntity();
                if (!standEntity.isArmsOnlyMode()) {
                    standEntity.moveTo(target.position());
                }
                if (target instanceof LivingEntity) {
                    standEntity.punch(task, this, task.getTarget());

                    if (EntityRange(userPower, 16) != null) {
                        if (standEntity.isArmsOnlyMode() && target.distanceTo(standEntity) <= 3) {
                            if (target instanceof LivingEntity) {
                                LivingEntity livingTarget = (LivingEntity) target;
                                livingTarget.heal(13);
                            }
                        } else if (!standEntity.isArmsOnlyMode()) {
                            if (target instanceof LivingEntity) {
                                LivingEntity livingTarget = (LivingEntity) target;
                                livingTarget.heal(13);
                            }
                        }

                    }
                }
            }
        }

    }


    public void onTaskSet(World world, StandEntity standEntity, IStandPower standPower, StandEntityAction.Phase phase, StandEntityTask task, int ticks) {
        if (standEntity.isArmsOnlyMode() && standEntity.swingingArm == Hand.OFF_HAND) {
            standEntity.setArmsOnlyMode(true, false);
        }
        if (standEntity.isArmsOnlyMode()) {
            manifest = false;
        }

    }


    public static LivingEntity EntityRange(IStandPower userPower, double range) {
        LivingEntity user = userPower.getUser();
        World world = user.level;
        LivingEntity entidad = world.getEntitiesOfClass(LivingEntity.class, user.getBoundingBox().inflate(range)).stream().filter(entity -> !entity.getActiveEffects().contains(new EffectInstance(Effects.REGENERATION))).findFirst().orElse(null);
        return entidad;
    }

    @Override
    protected boolean standKeepsTarget(ActionTarget target) {
        return target.getType() == ActionTarget.TargetType.ENTITY && target.getEntity() instanceof LivingEntity;
    }

    @Override
    public Action.TargetRequirement getTargetRequirement() {
        return Action.TargetRequirement.ENTITY;
    }

    @Override
    public boolean noAdheringToUserOffset(IStandPower standPower, StandEntity standEntity) {
        return !standEntity.isArmsOnlyMode();
    }

}