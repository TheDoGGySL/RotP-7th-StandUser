package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.thedoggys.rotp_7su.entity.BulletEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SpecialsBlueGrenade extends StandEntityAction {
    public SpecialsBlueGrenade(Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity entity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide) {
            TNTEntity tnt = new TNTEntity(world, entity.getX(), entity.getY(), entity.getZ(), entity);
            tnt.setFuse(60);
            Vector3d lookAngle = entity.getLookAngle();
            tnt.setDeltaMovement(lookAngle.x * 1.5, lookAngle.y * 1.5, lookAngle.z * 1.5);
            tnt.setPos(entity.getEyePosition(1).x, entity.getEyePosition(1).y, entity.getEyePosition(1).z);
            world.addFreshEntity(tnt);
            userPower.consumeStamina(100);
        }
    }
}


