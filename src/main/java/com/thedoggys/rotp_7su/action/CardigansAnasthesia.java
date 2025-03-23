package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.thedoggys.rotp_7su.init.InitEffects;
import com.thedoggys.rotp_7su.init.InitSounds;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class CardigansAnasthesia extends StandEntityAction {
    boolean manifest = true;
    public static final StandPose INJECT_TARGET = new StandPose("inject");

    public CardigansAnasthesia(Builder builder) {
        super(builder);
    }


    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide) {
            if (task.getTarget().getType() == ActionTarget.TargetType.ENTITY) {
                standEntity.playSound(InitSounds.CARDIGANS_INJECT.get(), 1f, 1f);
                Entity target = task.getTarget().getEntity();
                if (target instanceof LivingEntity) {
                    ((LivingEntity) target).addEffect(new EffectInstance(InitEffects.ANASTESIA_EFFECT.get(), 300, 0, false, false));
                }
            }
        }

    }


    @Override
    public void onTaskSet(World world, StandEntity standEntity, IStandPower standPower, Phase phase, StandEntityTask task, int ticks) {
        if (task.getPhase()==Phase.BUTTON_HOLD && !standEntity.isManuallyControlled()) {
            ActionTarget target=task.getTarget();
            LivingEntity entity=(LivingEntity) target.getEntity();
            LivingEntity user=standPower.getUser();
            if (entity instanceof LivingEntity && entity.isAlive() && user instanceof LivingEntity) {
                Vector3d dir_difference=entity.position().subtract(user.position());
                Vector3d normal_dir=dir_difference.normalize();
                standEntity.lookAt(EntityAnchorArgument.Type.EYES, normal_dir);
                standEntity.moveTo(entity.position().subtract(normal_dir));
            }
        }
    }



    @Override
    protected boolean standKeepsTarget(ActionTarget target) {
        return target.getType()== ActionTarget.TargetType.ENTITY && target.getEntity() instanceof LivingEntity && !(target.getEntity() instanceof StandEntity);
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.ENTITY;
    }

    @Override
    public boolean cancelHeldOnGettingAttacked(IStandPower power, DamageSource dmgSource, float dmgAmount) {
        return true;
    }

    @Override
    public boolean noAdheringToUserOffset(IStandPower standPower, StandEntity standEntity) {
        return true;
    }
}