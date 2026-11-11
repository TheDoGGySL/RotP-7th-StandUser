package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.thedoggys.rotp_7su.entity.ButterflyEntity;
import com.thedoggys.rotp_7su.init.power.InitStandEffects;
import com.thedoggys.rotp_7su.specials.SpecialsEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class SpecialsYellowThrow extends StandEntityAction {
    public SpecialsYellowThrow(Builder builder) {
        super(builder);
    }
    public static final StandPose THROW = new StandPose("knifeStackThrow");

    @Override
    public void onClick(World world, LivingEntity user, IStandPower power) {
        SpecialsEntities specialsEntities = IStandPower.getPlayerStandPower((PlayerEntity) user).getContinuousEffects().getOrCreateEffect(InitStandEffects.SPECIALS_SUMMONED_ENTITIES.get());
        power.stopHeldAction(false);
        specialsEntities.pickEntity(2);
        super.onClick(world, user, power);
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
            int staminaCost = knivesToThrow * 30;
            userPower.setCooldownTimer(this, cooldown);
            userPower.consumeStamina(staminaCost);
        }
    }
}

