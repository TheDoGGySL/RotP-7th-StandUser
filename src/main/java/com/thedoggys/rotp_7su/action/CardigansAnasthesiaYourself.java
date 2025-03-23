package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.thedoggys.rotp_7su.init.InitEffects;
import com.thedoggys.rotp_7su.init.InitSounds;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class CardigansAnasthesiaYourself extends StandEntityAction {
    public CardigansAnasthesiaYourself(Builder builder) {
        super(builder);
    }


    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide) {
            standEntity.playSound(InitSounds.CARDIGANS_INJECT.get(), 1f, 1f);
            userPower.getUser().addEffect(new EffectInstance(InitEffects.ANASTESIA_EFFECT.get(), 300, 0, false, false));
        }
    }
}
