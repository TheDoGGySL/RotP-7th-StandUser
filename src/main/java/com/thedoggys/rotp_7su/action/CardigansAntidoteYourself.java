package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class CardigansAntidoteYourself extends StandEntityAction {
    public CardigansAntidoteYourself(StandEntityAction.Builder builder) {
        super(builder);
    }


    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide) {
            userPower.getUser().removeEffect(Effects.POISON);
            userPower.getUser().removeEffect(Effects.WITHER);
            userPower.getUser().removeEffect(Effects.HUNGER);
            userPower.getUser().removeEffect(Effects.CONFUSION);
            userPower.getUser().removeEffect(Effects.BLINDNESS);
            // standEntity.playSound(InitSounds.SPICE_GIRL_ABILITY_FIRST.get(),1.0f,1.0f);
        }
    }
}
