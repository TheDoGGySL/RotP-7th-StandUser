package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.punch.StandBlockPunch;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.general.MathUtil;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

public class CardigansSlashFinisher extends StandEntityHeavyAttack {
    public static final StandPose HEAVY = new StandPose("heavyPunch");
    public CardigansSlashFinisher(StandEntityHeavyAttack.Builder builder) {
        super(builder);
    }

    @Override
    public int getStandWindupTicks(IStandPower standPower, StandEntity standEntity) {
        return Math.max(super.getStandWindupTicks(standPower, standEntity) - getStandActionTicks(standPower, standEntity) / 2, 1);
    }

    @Override
    public void standTickWindup(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (world.isClientSide()) {
            if (task.getTicksLeft() == 1) {
                SoundEvent sound = getPunchSwingSound();
                if (sound != null) {
                    standEntity.playSound(sound, 1.0F, 1.0F, ClientUtil.getClientPlayer());
                }

                double d0 = -MathHelper.sin(standEntity.yRot * MathUtil.DEG_TO_RAD);
                double d1 = MathHelper.cos(standEntity.yRot * MathUtil.DEG_TO_RAD);
                world.addParticle(ParticleTypes.SWEEP_ATTACK, standEntity.getX() + d0, standEntity.getY(0.5), standEntity.getZ() + d1,
                        d0, 0.0D, d1);
            }
        }
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()) {
            double reach = standEntity.getAttributeValue(ForgeMod.REACH_DISTANCE.get());
            world.getEntities(standEntity, standEntity.getBoundingBox().inflate(reach, 0, reach),
                    e -> !e.isSpectator() && e.isPickable() && standEntity.canHarm(e)).forEach(targetEntity -> {
                Vector3d standLookVec = standEntity.getLookAngle();
                Vector3d targetVec = targetEntity.position().subtract(standEntity.position()).normalize();
                double cos = standLookVec.dot(targetVec);
                if (cos > -0.5) {
                    StandEntityPunch slash = punchEntity(standEntity, targetEntity, standEntity.getDamageSource());
                    if (cos < 0.5) {
                        slash.damage(slash.getDamage() * 0.5F);
                    }
                    slash.doHit(task);
                }
            });
        }
    }

    @Override
    public StandEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
        if (target instanceof LivingEntity) {
            int duration = 100;
            int amplifier = 0;
            EffectInstance existingEffect = ((LivingEntity) target).getEffect(ModStatusEffects.BLEEDING.get());
            if (existingEffect != null) {
                duration += existingEffect.getDuration();
                amplifier = Math.min(existingEffect.getAmplifier() + 1, 2);
            }
            ((LivingEntity) target).addEffect(new EffectInstance(ModStatusEffects.BLEEDING.get(), duration, amplifier, false, false, true));
        }
        return super.punchEntity(stand, target, dmgSource)
                .impactSound(null)
                .addKnockback(1);
    }

    @Override
    public StandBlockPunch punchBlock(StandEntity stand, BlockPos pos, BlockState state, Direction face) {
        return new StandBlockPunch(stand, pos, state, face);
    }
}
