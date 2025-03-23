package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.thedoggys.rotp_7su.init.InitEffects;
import com.thedoggys.rotp_7su.init.InitSounds;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class CardigansDopping extends StandEntityAction {
    public CardigansDopping(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide) {
            standEntity.playSound(InitSounds.CARDIGANS_INJECT.get(), 1f, 1f);
            userPower.getUser().addEffect(new EffectInstance(InitEffects.DOPING_EFFECT.get(), 200, 0, false, false));
            // standEntity.playSound(InitSounds.SPICE_GIRL_ABILITY_FIRST.get(),1.0f,1.0f);
        }
    }
}