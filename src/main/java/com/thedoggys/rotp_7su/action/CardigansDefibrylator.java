package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.thedoggys.rotp_7su.init.InitEffects;
import com.thedoggys.rotp_7su.init.InitParticles;
import com.thedoggys.rotp_7su.init.InitSounds;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CardigansDefibrylator extends StandEntityLightAttack {
    public static final StandPose DEFIBRILATE_ANIM = new StandPose("defibrilate");

    public CardigansDefibrylator(Builder builder) {
        super(builder);
    }


    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide) {

            if (task.getTarget().getType() == ActionTarget.TargetType.ENTITY) {
                Entity target = task.getTarget().getEntity();

                if (target instanceof LivingEntity) {
                    standEntity.playSound(ModSounds.HAMON_SPARK.get(),1f, 1f);
                    if (target != null) {
                        if (world instanceof ServerWorld) {
                            ServerWorld serverWorld = (ServerWorld) world;
                            serverWorld.sendParticles(
                                    InitParticles.CARDIGANS_SHOCK.get(),
                                    target.getX(),
                                    target.getY() + target.getBbHeight()/2,
                                    target.getZ(),
                                    30,
                                    0.5,
                                    0.5,
                                    0.5,
                                    0.1
                            );
                        }
                    }
                }
                        ((LivingEntity) target).addEffect(new EffectInstance(InitEffects.SHOCKED.get(), 100, 0, false, false, false));
                        target.hurt(DamageSource.LIGHTNING_BOLT, 5);
                    }
                }
            }



    @Override
    public void onTaskSet(World world, StandEntity standEntity, IStandPower standPower, Phase phase, StandEntityTask task, int ticks) {
        if (task.getPhase()==Phase.BUTTON_HOLD && !standEntity.isManuallyControlled()) {
            standEntity.playSound(InitSounds.CARDIGANS_CHARGE.get(), 1f, 1f);
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