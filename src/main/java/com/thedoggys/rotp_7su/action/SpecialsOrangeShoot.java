package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.thedoggys.rotp_7su.entity.BulletEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SpecialsOrangeShoot extends StandEntityAction {
    public SpecialsOrangeShoot(Builder builder) {
        super(builder);
    }


    @Override
    public void standTickPerform(World world, StandEntity entity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide) {
            BulletEntity bullet = new BulletEntity(entity, world);
            Vector3d pos = entity.getEyePosition(1).subtract(0, bullet.getBbHeight() / 2, 0).add(entity.getLookAngle());
            bullet.shootFromRotation(entity, 2f, 0);
//                        pos = pos.add(bullet.getDeltaMovement().scale((double) i / bulletsPerTickForLulz));
            bullet.setPos(pos.x, pos.y, pos.z);
            world.addFreshEntity(bullet);
            entity.playSound(ModSounds.TOMMY_GUN_SHOT.get(),1f, 1f);
            userPower.consumeStamina(10f);
        }
    }
}

