package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.thedoggys.rotp_7su.init.InitSounds;
import net.minecraft.client.audio.SoundSource;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class CardigansHeal extends StandEntityAction {
    boolean manifest = true;


    public CardigansHeal(StandEntityAction.Builder builder) {
        super(builder);
    }


    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide) {

            LivingEntity user = userPower.getUser();

            if (task.getTarget().getType() == ActionTarget.TargetType.ENTITY) {
                Entity target = task.getTarget().getEntity();
                if (target instanceof LivingEntity) {
                    LivingEntity livingTarget = (LivingEntity) target;
                    livingTarget.heal(13);
                    standEntity.playSound(InitSounds.CARDIGANS_HEAL.get(), 1f, 1f);
                    if (task.getTarget().getType() == ActionTarget.TargetType.ENTITY) {
                        if (target instanceof LivingEntity && userPower.getResolveLevel() >= 3) {
                            //standEntity.playSound(InitSounds.CARDIGANS_INJECT.get(), 1f, 1f);
                            livingTarget.removeEffect(ModStatusEffects.BLEEDING.get());
                            livingTarget.removeEffect(Effects.POISON);
                            livingTarget.removeEffect(Effects.WITHER);
                            livingTarget.removeEffect(Effects.HUNGER);
                            livingTarget.removeEffect(Effects.CONFUSION);
                            userPower.getUser().removeEffect(Effects.BLINDNESS);
                        }
                    }
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