package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.punch.StandBlockPunch;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.entity.stand.StandStatFormulas;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.general.MathUtil;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.thedoggys.rotp_7su.entity.JoykillerEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

public class JoykillerFinisherAttack extends StandEntityHeavyAttack {
    public static final StandPose FINISHER_ANIM = new StandPose("finisherAttack");

    public JoykillerFinisherAttack(StandEntityHeavyAttack.Builder builder) {
        super(builder);
    }

    @Override
    protected ActionConditionResult checkStandConditions(StandEntity stand, IStandPower power, ActionTarget target) {
        if (stand instanceof JoykillerEntity && !((JoykillerEntity) stand).hasAxe()) {
            return conditionMessage("joykiller_axe");
        }
        return super.checkStandConditions(stand, power, target);
    }

    @Override
    public int getStandWindupTicks(IStandPower standPower, StandEntity standEntity) {
        return 0;
    }

    @Override
    public int getStandActionTicks(IStandPower standPower, StandEntity standEntity) {
        return Math.max(StandStatFormulas.getHeavyAttackWindup(standEntity.getAttackSpeed(), standEntity.getLastHeavyFinisherValue()), 2);
    }

    @Override
    public void phaseTransition(World world, StandEntity standEntity, IStandPower standPower,
                                Phase from, Phase to, StandEntityTask task, int ticks) {
        super.phaseTransition(world, standEntity, standPower, from, to, task, ticks);
        if (to == Phase.PERFORM) {
            if (standEntity.isFollowingUser() && standEntity.getAttackSpeed() < 24) {
                LivingEntity user = standEntity.getUser();
                if (user != null) {
                    Vector3d vec = new Vector3d(0, 0, 1).yRot(-user.yRot * MathUtil.DEG_TO_RAD);
                    standEntity.setPos(user.getX() + vec.x,
                            standEntity.getY(),
                            user.getZ() + vec.z);
                }
            }
            task.getAdditionalData().push(Vector3d.class, standEntity.getLookAngle().scale(10D / (double) ticks));
        }
    }

    @Override
    public void standTickPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        float completion = task.getPhaseCompletion(1.0F);
        boolean lastTick = task.getTicksLeft() <= 1;
        boolean moveForward = completion <= 0.5F;

        if (moveForward) {
            for (RayTraceResult rayTraceResult : JojoModUtil.rayTraceMultipleEntities(standEntity,
                    standEntity.getAttributeValue(ForgeMod.REACH_DISTANCE.get()) * 2,
                    standEntity.canTarget(),
                    RayTraceContext.BlockMode.COLLIDER,
                    standEntity.getPrecision(),
                    1f)) {
                standEntity.punch(task, this, ActionTarget.fromRayTraceResult(rayTraceResult));
            }
        }

        if (!world.isClientSide() && lastTick && standEntity.isFollowingUser()) {
            standEntity.retractStand(false);
        }
        standEntity.setDeltaMovement(moveForward ? task.getAdditionalData().peek(Vector3d.class) : Vector3d.ZERO);
    }

    @Override
    public StandEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
        StandEntityPunch punch = super.punchEntity(stand, target, dmgSource)
                .impactSound(null)
                .addKnockback(1.5F);
        if (!stand.level.isClientSide() && target instanceof LivingEntity) {
            ((LivingEntity) target).addEffect(new EffectInstance(
                    ModStatusEffects.BLEEDING.get(),
                    160,
                    1,
                    false,
                    false
            ));
        }
        if (stand.getAttackSpeed() < 24) {
            boolean left = MathHelper.wrapDegrees(
                    stand.yRot - MathUtil.yRotDegFromVec(punch.target.position().subtract(stand.position())))
                    < 0;
            return punch.knockbackYRotDeg((60F + stand.getRandom().nextFloat() * 30F) * (left ? 1 : -1));
        }
        return punch.knockbackXRot(-90F);
    }

    @Override
    public StandBlockPunch punchBlock(StandEntity stand, BlockPos pos, BlockState state, Direction face) {
        return new StandBlockPunch(stand, pos, state);
    }

    @Override
    public void standTickWindup(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (world.isClientSide()) {
            SoundEvent sound = getPunchSwingSound();
            if (sound != null) {
                standEntity.playSound(sound, 1.0F, 1.0F, ClientUtil.getClientPlayer());
            }

            double d0 = -MathHelper.sin(standEntity.yRot * MathUtil.DEG_TO_RAD);
            double d1 = MathHelper.cos(standEntity.yRot * MathUtil.DEG_TO_RAD);
            world.addParticle(ParticleTypes.SWEEP_ATTACK,
                    standEntity.getX() + d0,
                    standEntity.getY(0.5),
                    standEntity.getZ() + d1,
                    d0, 0.0D, d1);
        }
    }

    @Override
    protected boolean standKeepsTarget(ActionTarget target) {
        return false;
    }

    @Override
    public boolean isChainable(IStandPower standPower, StandEntity standEntity) {
        return true;
    }

    @Override
    protected boolean standMovesByItself(IStandPower standPower, StandEntity standEntity) {
        return true;
    }
}
