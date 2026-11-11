package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.stand.StandEntityMeleeBarrage;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.thedoggys.rotp_7su.init.power.InitStandEffects;
import com.thedoggys.rotp_7su.specials.SpecialsEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class SpecialRedBarrage extends StandEntityMeleeBarrage {
    public SpecialRedBarrage(Builder builder) {
        super(builder);
    }

    @Override
    public void onClick(World world, LivingEntity user, IStandPower power) {
        SpecialsEntities specialsEntities = IStandPower.getPlayerStandPower((PlayerEntity) user).getContinuousEffects().getOrCreateEffect(InitStandEffects.SPECIALS_SUMMONED_ENTITIES.get());
        power.stopHeldAction(false);
        specialsEntities.pickEntity(0);
        super.onClick(world, user, power);
    }
}
