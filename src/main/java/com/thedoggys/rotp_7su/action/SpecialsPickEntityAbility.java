package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.thedoggys.rotp_7su.client.ui.screen.specials.ChangeFormationScreen;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

// Возможно, тут стоит скопипастить интерфейс из Bad Company, абилку SelectFormation, например
public class SpecialsPickEntityAbility extends StandAction {
    public SpecialsPickEntityAbility(StandAction.Builder builder){
        super(builder);
    }

    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if(world.isClientSide && user == com.github.standobyte.jojo.client.ClientUtil.getClientPlayer()){
            ChangeFormationScreen.openWindowOnClick();
        }
    }
}
