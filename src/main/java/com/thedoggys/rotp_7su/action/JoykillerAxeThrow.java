package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.thedoggys.rotp_7su.entity.AxeEntity;
import com.thedoggys.rotp_7su.entity.JoykillerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class JoykillerAxeThrow extends StandEntityAction {
    public static final StandPose THROW_ANIM = new StandPose("axeThrow");
    public JoykillerAxeThrow(Builder builder) {
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
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()) {
            JoykillerEntity chariot = (JoykillerEntity) standEntity;
            AxeEntity rapier = new AxeEntity(standEntity, world);
            Entity aimingEntity = chariot;
            if (chariot.isFollowingUser()) {
                LivingEntity user = userPower.getUser();
                if (user != null) {
                    aimingEntity = user;
                }
            }
            rapier.setPos(aimingEntity.getX(), aimingEntity.getEyeY(), aimingEntity.getZ());
            standEntity.shootProjectile(rapier, 2F, 0);
            if (!userPower.isUserCreative()) {
                chariot.setHasAxe(false);
            }
        }
    }
    @Override
    public void onTaskSet(World world, StandEntity standEntity, IStandPower standPower,
                          Phase phase, StandEntityTask task, int ticks) {
        if (!world.isClientSide() && standEntity instanceof JoykillerEntity) {
            // This flag determines if a pickaxe model is rendered in the Stand's hand (see ExampleStandModel).
            ((JoykillerEntity) standEntity).setHasAxe(true);
        }
    }

    @Override
    public void phaseTransition(World world, StandEntity standEntity, IStandPower standPower,
                                @Nullable Phase from, @Nullable Phase to, StandEntityTask task, int nextPhaseTicks) {
        if (!world.isClientSide() && to == Phase.PERFORM && standEntity instanceof JoykillerEntity) {
            ((JoykillerEntity) standEntity).setHasAxe(false);
        }
    }

    @Override
    protected boolean standKeepsTarget(ActionTarget target) {
        return true;
    }
}
