package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.entity.stand.StandStatFormulas;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.general.GeneralUtil;
import com.thedoggys.rotp_7su.entity.BulletEntity;
import com.thedoggys.rotp_7su.init.power.InitStandEffects;
import com.thedoggys.rotp_7su.specials.SpecialsEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SpecialsOrangeShoot extends StandEntityAction {
    public SpecialsOrangeShoot(Builder builder) {
        super(builder);
    }
    public static final StandPose SHOOT = new StandPose("shoot");

    @Override
    public void onClick(World world, LivingEntity user, IStandPower power) {
        SpecialsEntities specialsEntities = IStandPower.getPlayerStandPower((PlayerEntity) user).getContinuousEffects().getOrCreateEffect(InitStandEffects.SPECIALS_SUMMONED_ENTITIES.get());
        power.stopHeldAction(false);
        specialsEntities.pickEntity(1);
        super.onClick(world, user, power);
    }

    @Override
    public void standTickPerform(World world, StandEntity entity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()) {
            GeneralUtil.doFractionTimes(() -> {

                BulletEntity bullet = new BulletEntity(entity, world);
                Vector3d pos = entity.getEyePosition(1).subtract(0, bullet.getBbHeight() / 2 + 0.05, 0).add(entity.getLookAngle());
                bullet.shootFromRotation(entity, 2f, 0);
//                        pos = pos.add(bullet.getDeltaMovement().scale((double) i / bulletsPerTickForLulz));
                bullet.setPos(pos.x, pos.y, pos.z);
                world.addFreshEntity(bullet);
                entity.playSound(ModSounds.TOMMY_GUN_SHOT.get(), 1f, 1f);
            }, StandStatFormulas.projectileFireRateScaling(entity, userPower) * 0.6);
        }
    }
}

