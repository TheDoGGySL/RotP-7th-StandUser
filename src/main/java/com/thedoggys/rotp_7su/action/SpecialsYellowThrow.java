package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.itemprojectile.KnifeEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.itemtracking.itemcap.TrackerItemStack;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.thedoggys.rotp_7su.entity.BulletEntity;
import com.thedoggys.rotp_7su.entity.ButterflyEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SpecialsYellowThrow extends StandEntityAction {
    public SpecialsYellowThrow(Builder builder) {
        super(builder);
    }


    @Override
    public void standPerform(World world, StandEntity entity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide) {
            float staminaRatio = userPower.getStamina() / (float) userPower.getMaxStamina();
            int knivesToThrow = Math.max(1, Math.min(5, (int) (5 * staminaRatio)));

            for (int i = 0; i < knivesToThrow; i++) {
                ButterflyEntity knifeEntity = new ButterflyEntity(world, entity);
                knifeEntity.setTimeStopFlightTicks(5);
                knifeEntity.shootFromRotation(entity, 1.5F, i == 0 ? 1.0F : 16.0F);
                world.addFreshEntity(knifeEntity);
            }

            world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    knivesToThrow == 1 ? ModSounds.KNIFE_THROW.get() : ModSounds.KNIVES_THROW.get(),
                    SoundCategory.PLAYERS, 0 * 0.5F, 0.4F / (entity.getRandom().nextFloat() * 0.4F + 0.8F));

            int cooldown = knivesToThrow * 3;
        }
    }
}

