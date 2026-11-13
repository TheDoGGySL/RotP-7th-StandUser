package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.thedoggys.rotp_7su.init.power.InitStandEffects;
import com.thedoggys.rotp_7su.specials.SpecialsEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SpecialsBlueGrenade extends StandEntityAction {
    public SpecialsBlueGrenade(Builder builder) {
        super(builder);
    }
    public static final StandPose GRENADE_THROW = new StandPose("grenadeThrow");

    @Override
    public void onClick(World world, LivingEntity user, IStandPower power) {
        SpecialsEntities specialsEntities = IStandPower.getPlayerStandPower((PlayerEntity) user).getContinuousEffects().getOrCreateEffect(InitStandEffects.SPECIALS_SUMMONED_ENTITIES.get());
        power.stopHeldAction(false);
        specialsEntities.pickEntity(4);
        super.onClick(world, user, power);
    }

    @Override
    public void standPerform(World world, StandEntity entity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide) {
            //todo заменить tnt на GrenadeEntity
            TNTEntity tnt = new TNTEntity(world, entity.getX(), entity.getY(), entity.getZ(), entity);
            tnt.setFuse(60);
            TNTEntity tnt2 = new TNTEntity(world, entity.getX(), entity.getY(), entity.getZ(), entity);
            tnt2.setFuse(60);
            Vector3d lookAngle = entity.getLookAngle();
            tnt.setDeltaMovement(lookAngle.x * 1.55, lookAngle.y * 1.5, lookAngle.z * 1.45);
            tnt.setPos(entity.getEyePosition(1).x, entity.getEyePosition(1).y, entity.getEyePosition(1).z);
            world.addFreshEntity(tnt);
            tnt2.setDeltaMovement(lookAngle.x * 1.45, lookAngle.y * 1.5, lookAngle.z * 1.55);
            tnt2.setPos(entity.getEyePosition(1).x, entity.getEyePosition(1).y, entity.getEyePosition(1).z);
            world.addFreshEntity(tnt2);
            userPower.consumeStamina(300);
        }
    }
}


